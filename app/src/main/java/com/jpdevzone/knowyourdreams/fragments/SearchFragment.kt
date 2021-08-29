package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.adapters.AlphabetAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener, AlphabetAdapter.OnLetterClickListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mRecyclerView1: RecyclerView
    private lateinit var mRecyclerView2: RecyclerView
    private lateinit var mLayoutManager1: RecyclerView.LayoutManager
    private lateinit var mLayoutManager2: RecyclerView.LayoutManager
    private lateinit var mAdapter1: RecyclerView.Adapter<*>
    private lateinit var mAdapter2: RecyclerView.Adapter<*>
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter2 as RecyclerViewAdapter).filter.filter(newText)
                return false
            }
        })

        mRecyclerView1 = binding.alphabet
        mRecyclerView1.setHasFixedSize(true)
        mLayoutManager1 = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mAdapter1 = AlphabetAdapter(alphabet(), this)

        mRecyclerView1.layoutManager = mLayoutManager1
        mRecyclerView1.adapter = mAdapter1

        mRecyclerView2 = binding.searchRecyclerView
        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        mLayoutManager2 = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter2 = RecyclerViewAdapter(Constants.dreamList as ArrayList<Dream>, this)

        mRecyclerView2.layoutManager = mLayoutManager2
        mRecyclerView2.adapter = mAdapter2

        MobileAds.initialize(requireContext()) {}
        createPersonalizedAdd()
    }

    private fun alphabet(): ArrayList<String> {
        val list = ArrayList<String>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add("$i")
        }
        return list
    }

    override fun onLetterClick(letter: String) {
        when (letter) {
            "А" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0,0)}
            "Б" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(140,0)}
            "В" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(436,0)}
            "Г" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(612,0)}
            "Д" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(809,0)}
            "Е" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(985,0)}
            "Ж" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1073,0)}
            "З" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1129,0)}
            "И" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1305,0)}
            "Й" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1434,0)}
            "К" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1438,0)}
            "Л" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1861,0)}
            "М" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1994,0)}
            "Н" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(2224,0)}
            "О" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(2383,0)}
            "П" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(2558,0)}
            "Р" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(3209,0)}
            "С" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(3406,0)}
            "Т" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(3799,0)}
            "У" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(3941,0)}
            "Ф" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4004,0)}
            "Х" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4091,0)}
            "Ц" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4184,0)}
            "Ч" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4234,0)}
            "Ш" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4315,0)}
            "Щ" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4384,0)}
            "Ъ" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4399,0)}
            "Ю" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4401,0)}
            "Я" -> {(mRecyclerView2.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(4415,0)}
        }
    }

    override fun onItemClick(item: String, definition: String, currentItem: Dream) {
        Constants.adCounter++
        when (Constants.adCounter % 5 == 0) {
            true -> {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                    Constants.history.add(currentItem)
                    val args = Bundle()
                    args.putString("Item", item)
                    args.putString("Definition", definition)

                    val inflatedFragment = InflatedItemFragment()
                    inflatedFragment.arguments = args
                    val fm = requireActivity().supportFragmentManager

                    inflatedFragment.show(fm, "inflatedItem")
                    createPersonalizedAdd()
                } else {
                    Constants.history.add(currentItem)
                    val args = Bundle()
                    args.putString("Item", item)
                    args.putString("Definition", definition)

                    val inflatedFragment = InflatedItemFragment()
                    inflatedFragment.arguments = args
                    val fm = requireActivity().supportFragmentManager

                    inflatedFragment.show(fm, "inflatedItem")
                    createPersonalizedAdd()
                }
            }

            false -> {
                Constants.history.add(currentItem)
                val args = Bundle()
                args.putString("Item", item)
                args.putString("Definition", definition)

                val inflatedFragment = InflatedItemFragment()
                inflatedFragment.arguments = args
                val fm = requireActivity().supportFragmentManager

                inflatedFragment.show(fm, "inflatedItem")
            }
        }
        println(Constants.adCounter)
    }

    private fun createPersonalizedAdd() {
        val adRequest = AdRequest.Builder().build()
        createInterstitialAdd(adRequest)
    }

    private fun createInterstitialAdd(adRequest: AdRequest) {
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
