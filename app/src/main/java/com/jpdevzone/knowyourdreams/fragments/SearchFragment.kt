package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.adapters.AlphabetAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchLayout = inflater.inflate(R.layout.fragment_search, container, false)

        val mRecyclerView1: RecyclerView = searchLayout.findViewById(R.id.alphabet)
        mRecyclerView1.setHasFixedSize(true)
        val mLayoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(context,
            RecyclerView.HORIZONTAL,false)
        val mAdapter1: RecyclerView.Adapter<*> = AlphabetAdapter(alphabet())

        mRecyclerView1.layoutManager = mLayoutManager1
        mRecyclerView1.adapter = mAdapter1

        val dreams = Constants.getDreams()

        val mRecyclerView2: RecyclerView = searchLayout.findViewById(R.id.searchRecyclerView)
        mRecyclerView2.setHasFixedSize(true)
        mRecyclerView2.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        val mLayoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val mAdapter2: RecyclerView.Adapter<*> = RecyclerViewAdapter(dreams)

        mRecyclerView2.layoutManager = mLayoutManager2
        mRecyclerView2.adapter = mAdapter2

        return searchLayout
    }

    private fun alphabet(): ArrayList<String> {
        val list = ArrayList<String>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add("$i")
        }
        return list
    }
}