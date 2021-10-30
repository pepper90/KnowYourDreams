package com.jpdevzone.knowyourdreams.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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

        //Architecture components________________________________________________

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DreamDatabase.getInstance(application).dreamDatabaseDao
        val viewModelFactory = SearchViewModelFactory(dataSource, application)
        val searchViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(SearchViewModel::class.java)

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

        searchViewModel.dreams.observe(viewLifecycleOwner, {
            searchAdapter.submitList(it)
        })

        searchViewModel.navigateToDreamData.observe(viewLifecycleOwner, {dreamId ->
            dreamId?.let {
                clickCounter++
                when (clickCounter % 5 == 0) {
                    true -> {
                        showAd(SearchFragmentDirections
                            .actionSearchFragmentToInflatedItemFragment(dreamId))
                        searchViewModel.onDreamNavigated()
                        searchViewModel.addToHistory(dreamId)
                    }

                    false -> {
                        navigate(SearchFragmentDirections
                            .actionSearchFragmentToInflatedItemFragment(dreamId))
                        searchViewModel.onDreamNavigated()
                        searchViewModel.addToHistory(dreamId)
                    }
                }
                println(clickCounter)
            }
        })

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

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    when (searchView.isIconified) {
                        false -> {
                            searchView.isIconified = true
                            searchView.isIconified = true
                            searchView.clearFocus()
                        }
                        else -> {
                            if (isEnabled) {
                                Toasty.custom(
                                    context!!,
                                    R.string.toast,
                                    R.drawable.ic_exit,
                                    R.color.blue_700,
                                    Toast.LENGTH_LONG,
                                    true,
                                    true
                                ).show()
                                isEnabled = false
                            } else {
                                requireActivity().onBackPressed()
                            }
                        }
                    }
                }
            }
            )

        MobileAds.initialize(requireContext()) {}
        loadAd()

        return binding.root
    }


    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

    private fun searchDatabase(query: String, viewModel: SearchViewModel, adapter: SearchAdapter) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, {adapter.submitList(it)})
    }

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

    private fun showAd(destination: NavDirections) {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    navigate(destination)
                    mInterstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    navigate(destination)
                    mInterstitialAd = null
                }

                override fun onAdShowedFullScreenContent() {
                    navigate(destination)
                }
            }
            mInterstitialAd?.show(requireContext() as Activity)
        } else {
            navigate(destination)
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        createInterstitialAd(adRequest)
    }

    private fun createInterstitialAd(adRequest: AdRequest) {
        InterstitialAd.load(requireContext(),"ca-app-pub-7588987461083278/8656642342", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }
}
