package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.adapters.AlphabetAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), RecyclerViewAdapter.OnItemClickListener, AlphabetAdapter.OnLetterClickListener {
    private lateinit var binding: FragmentSearchBinding
    private var mRecyclerView1: RecyclerView? = null
    private var mRecyclerView2: RecyclerView? = null
    private var mLayoutManager1: RecyclerView.LayoutManager? = null
    private var mLayoutManager2: RecyclerView.LayoutManager? = null
    private var mAdapter1: RecyclerView.Adapter<*>? = null
    private var mAdapter2: RecyclerView.Adapter<*>? = null
    private val dreams = Constants.getDreams()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        mRecyclerView1 = binding.alphabet
        mRecyclerView1!!.setHasFixedSize(true)
        mLayoutManager1 = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL, false
        )
        mAdapter1 = AlphabetAdapter(alphabet(), this)

        mRecyclerView1!!.layoutManager = mLayoutManager1
        mRecyclerView1!!.adapter = mAdapter1

        mRecyclerView2 = binding.searchRecyclerView
        mRecyclerView2!!.setHasFixedSize(true)
        mRecyclerView2!!.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )
        mLayoutManager2 = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter2 = RecyclerViewAdapter(dreams, this)

        mRecyclerView2!!.layoutManager = mLayoutManager2
        mRecyclerView2!!.adapter = mAdapter2

        return binding.root
    }

    private fun alphabet(): ArrayList<String> {
        val list = ArrayList<String>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add("$i")
        }
        return list
    }

    override fun onItemClick(position: Int, item: String, definition: String) {
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }

    override fun onLetterClick(letter: String) {
        when (letter) {
            "А" -> {(mRecyclerView2!!.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0,0)}
            "Б" -> {(mRecyclerView2!!.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(140,0)}
            "В" -> {}
            "Г" -> {}
            "Д" -> {}
            "Е" -> {}
            "Ж" -> {}
            "З" -> {}
            "И" -> {}
            "Й" -> {}
            "К" -> {}
            "Л" -> {}
            "М" -> {}
            "Н" -> {}
            "О" -> {}
            "П" -> {}
            "Р" -> {}
            "С" -> {}
            "Т" -> {}
            "У" -> {}
            "Ф" -> {}
            "Х" -> {}
            "Ц" -> {}
            "Ч" -> {}
            "Ш" -> {}
            "Щ" -> {}
            "Ъ" -> {}
            "Ю" -> {}
            "Я" -> {}
        }

    }

}
