package com.jpdevzone.knowyourdreams.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentSearchBinding
import es.dmoral.toasty.Toasty

class SearchFragment : Fragment() {
    private var mInterstitialAd: InterstitialAd? = null
    private var clickCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        loadData()

        //Architecture components________________________________________________

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DreamDatabase.getInstance(application).dreamDatabaseDao
        val viewModelFactory = SearchViewModelFactory(dataSource, application)
        val searchViewModel =
            ViewModelProvider(
                this, viewModelFactory)[SearchViewModel::class.java]

        binding.searchViewModel = searchViewModel
        binding.lifecycleOwner = this

        //DREAM LIST_____________________________________________________________

        val searchRecyclerView = binding.searchRecyclerView
        val searchAdapter = SearchAdapter(
            DreamClickListener {
                dreamId -> searchViewModel.onDreamClicked(dreamId)
            },
            DreamCheckListener { dream ->
                searchViewModel.updateChecked(dream)
            })
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        searchViewModel.dreams.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it)
        }

        searchViewModel.navigateToDreamData.observe(viewLifecycleOwner) { dreamId ->
            dreamId?.let {
                clickCounter++
                when (clickCounter % 5 == 0) {
                    true -> {
                        showAd()
                        navigate(
                            SearchFragmentDirections
                                .actionSearchFragmentToInflatedItemFragment(dreamId)
                        )
                        searchViewModel.onDreamNavigated()
                        searchViewModel.addToHistory(dreamId)
                    }

                    false -> {
                        navigate(
                            SearchFragmentDirections
                                .actionSearchFragmentToInflatedItemFragment(dreamId)
                        )
                        searchViewModel.onDreamNavigated()
                        searchViewModel.addToHistory(dreamId)
                        saveData()
                    }
                }
                Log.i("INFO", "$clickCounter")
            }
        }

        //ALPHABET________________________________________________________________

        val alphabetRecyclerView = binding.alphabetRecyclerView
        val alphabetAdapter = AlphabetAdapter(LetterClickListener {
                letter ->
                (searchRecyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                    searchViewModel.map(letter),0)
        })

        alphabetRecyclerView.adapter = alphabetAdapter
        alphabetAdapter.submitList(searchViewModel.alphabet())

        //SEARCHVIEW-------------------------------------------------------------

        val searchView = binding.searchView
        searchViewStyling(searchView)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchDatabase(query,searchViewModel,searchAdapter)
                }
                return true
            }
        })

        MobileAds.initialize(requireContext()) {}
        loadAd()
        rateApp(requireActivity())
        return binding.root
    }


    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

    private fun searchDatabase(query: String, viewModel: SearchViewModel, adapter: SearchAdapter) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    @SuppressLint("DiscouragedApi")
    private fun searchViewStyling(searchView: SearchView) {
        val input = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val searchText = searchView.findViewById<View>(input) as TextView
        val font = ResourcesCompat.getFont(requireContext(), R.font.oswald_light)
        searchText.typeface = font

        searchView.setOnClickListener {
            searchView.isIconified = false
        }
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(),"ca-app-pub-7588987461083278/7970508644", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdMob", adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("AdMob", "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d("AdMob", "Ad was dismissed.")
                    mInterstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d("AdMob", "Ad failed to show.")
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("AdMob", "Ad showed fullscreen content.")
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(requireContext() as Activity)
        } else {
            Toasty.custom(requireContext(), R.string.noAdLoaded,R.drawable.ic_no_ads,R.color.black,
                Toast.LENGTH_LONG,true, true).show()
        }
    }

    private fun saveData() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putInt("clickCounter", clickCounter)
            apply()
        }
    }
    private fun loadData() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE) ?: return
        clickCounter = sharedPref.getInt("clickCounter", 0)
    }

    fun rateApp(activity: Activity){
        val manager: ReviewManager = ReviewManagerFactory.create(activity.applicationContext)
        val request: Task<ReviewInfo> = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We can get the ReviewInfo object
                val reviewInfo: ReviewInfo = task.result
                val flow: Task<Void> =
                    manager.launchReviewFlow(activity, reviewInfo)
                flow.addOnCompleteListener { }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
