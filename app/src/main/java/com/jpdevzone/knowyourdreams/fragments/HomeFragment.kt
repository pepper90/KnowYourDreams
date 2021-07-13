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
import com.jpdevzone.knowyourdreams.adapters.HomeAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeLayout = inflater.inflate(R.layout.fragment_home, container, false)

        val searched = Constants.getDreams()

        val homeRecyclerView: RecyclerView = homeLayout.findViewById(R.id.homeRecyclerView)
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val mAdapter: RecyclerView.Adapter<*> = HomeAdapter(searched)

        homeRecyclerView.layoutManager = mLayoutManager
        homeRecyclerView.adapter = mAdapter

        return homeLayout
    }
}