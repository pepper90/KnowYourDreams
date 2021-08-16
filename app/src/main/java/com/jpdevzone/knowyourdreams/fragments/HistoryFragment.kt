package com.jpdevzone.knowyourdreams.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.setHasFixedSize(true)
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val random = Constants.dreamList.shuffled()[1]

        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (Constants.history.isNotEmpty()) {
                binding.tvEmptyHistory.visibility = View.GONE
                historyRecyclerView.visibility = View.VISIBLE

                mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
                mAdapter = if (Constants.history.size == 1) {
                    HistoryAdapter(Constants.history, this)
                }else{
                    HistoryAdapter(Constants.history.reversed() as ArrayList<Dream>,this)
                }

                historyRecyclerView.layoutManager = mLayoutManager
                historyRecyclerView.adapter = mAdapter
            } else {
                binding.tvEmptyHistory.visibility = View.VISIBLE
                historyRecyclerView.visibility = View.GONE
            }
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