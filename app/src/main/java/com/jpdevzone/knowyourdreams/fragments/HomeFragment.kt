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
import com.jpdevzone.knowyourdreams.adapters.HomeAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val searched = Constants.getDreams()

        val homeRecyclerView: RecyclerView = binding.homeRecyclerView
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val mAdapter: RecyclerView.Adapter<*> = HomeAdapter(searched)

        homeRecyclerView.layoutManager = mLayoutManager
        homeRecyclerView.adapter = mAdapter

        val random = Constants.getDreams().shuffled()[1]
        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition

        return binding.root
    }
}