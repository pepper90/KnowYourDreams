package com.jpdevzone.knowyourdreams.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.adapters.HistoryAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val searched = Constants.getDreams()

        val historyRecyclerView: RecyclerView = binding.historyRecyclerView
        historyRecyclerView.setHasFixedSize(true)
        historyRecyclerView.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val mAdapter: RecyclerView.Adapter<*> = HistoryAdapter(searched)

        historyRecyclerView.layoutManager = mLayoutManager
        historyRecyclerView.adapter = mAdapter

        val random = Constants.getDreams()[140]
        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.visibility = View.VISIBLE
    }

}