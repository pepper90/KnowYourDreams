package com.jpdevzone.knowyourdreams.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.adapters.HistoryAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerView.Adapter<*>
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
        if (history.size == 0) {
            binding.tvEmptyHistory.visibility = View.VISIBLE
        } else {
            binding.tvEmptyHistory.visibility = View.GONE
        }
        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
        mAdapter = HistoryAdapter(history,this)

        historyRecyclerView.layoutManager = mLayoutManager
        historyRecyclerView.adapter = mAdapter

        val random = Constants.getDreams().shuffled()[1]

        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            historyRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private fun history(currentItem: Dream): ArrayList<Dream> {
        val limit = 4
        if (history.size > limit) {
            history.add(currentItem)
            history.remove(history[0])
        } else {
            history.add(currentItem)
        }
        return history
    }

    override fun onItemClick(item: String, definition: String, currentItem: Dream) {
        if (!history.contains(currentItem)) {
            history(currentItem)
        }else{
            history.remove(currentItem)
            history.add(history.size, currentItem)
        }
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }
}