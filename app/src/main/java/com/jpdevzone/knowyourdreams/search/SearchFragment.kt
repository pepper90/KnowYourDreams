package com.jpdevzone.knowyourdreams.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
//    private var mInterstitialAd: InterstitialAd? = null

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
            dreamId?.let {  navigate(SearchFragmentDirections
                .actionSearchFragmentToInflatedItemFragment(dreamId))
                searchViewModel.onDreamNavigated()
                searchViewModel.addToHistory(dreamId)
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

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)



//        MobileAds.initialize(requireContext()) {}
//        createPersonalizedAdd()
//    }


//    override fun onItemClick(item: String, definition: String, currentItem: Dream) {
//        Constants.adCounter++
//        when (Constants.adCounter % 5 == 0) {
//            true -> {
//                if (mInterstitialAd != null) {
//                    mInterstitialAd?.show(requireActivity())
//                    Constants.history.add(currentItem)
//                    val args = Bundle()
//                    args.putString("Item", item)
//                    args.putString("Definition", definition)
//
//                    val inflatedFragment = InflatedItemFragment()
//                    inflatedFragment.arguments = args
//                    val fm = requireActivity().supportFragmentManager
//
//                    inflatedFragment.show(fm, "inflatedItem")
//                    createPersonalizedAdd()
//                } else {
//                    Constants.history.add(currentItem)
//                    val args = Bundle()
//                    args.putString("Item", item)
//                    args.putString("Definition", definition)
//
//                    val inflatedFragment = InflatedItemFragment()
//                    inflatedFragment.arguments = args
//                    val fm = requireActivity().supportFragmentManager
//
//                    inflatedFragment.show(fm, "inflatedItem")
//                    createPersonalizedAdd()
//                }
//            }
//
//            false -> {
//                Constants.history.add(currentItem)
//                val args = Bundle()
//                args.putString("Item", item)
//                args.putString("Definition", definition)
//
//                val inflatedFragment = InflatedItemFragment()
//                inflatedFragment.arguments = args
//                val fm = requireActivity().supportFragmentManager
//
//                inflatedFragment.show(fm, "inflatedItem")
//            }
//        }
//        println(Constants.adCounter)
//    }
//
//    private fun createPersonalizedAdd() {
//        val adRequest = AdRequest.Builder().build()
//        createInterstitialAdd(adRequest)
//    }
//
//    private fun createInterstitialAdd(adRequest: AdRequest) {
//        InterstitialAd.load(requireContext(),"ca-app-pub-7588987461083278/8656642342", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd: InterstitialAd) {
//                mInterstitialAd = interstitialAd
//            }
//        })
//    }
}
