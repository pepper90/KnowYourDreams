package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.adapters.HistoryAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private val history = Constants.history

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val random = Constants.getDreams().shuffled()[1]

        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (history.size == 0) {
                binding.tvEmptyHistory.visibility = View.VISIBLE
            } else {
                binding.tvEmptyHistory.visibility = View.GONE
            }
            val historyRecyclerView: RecyclerView = binding.historyRecyclerView
            historyRecyclerView.setHasFixedSize(true)
            val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
            val mAdapter: RecyclerView.Adapter<*> = HistoryAdapter(history,this)

            historyRecyclerView.layoutManager = mLayoutManager
            historyRecyclerView.adapter = mAdapter
        }
    }

    override fun onItemClick(item: String, definition: String) {
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }
}