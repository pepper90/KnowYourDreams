package com.jpdevzone.knowyourdreams.history

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.database.Constants
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.databinding.FragmentHistoryBinding
import com.jpdevzone.knowyourdreams.inflateditem.InflatedItemFragment
import es.dmoral.toasty.Toasty

class HistoryFragment : Fragment(), HistoryAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var clearData: ImageButton
//    private val random = Constants.dreamList.shuffled()[1]
    private lateinit var copy: ImageButton
    private lateinit var share: ImageButton
    private lateinit var addToFavs: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clearData = binding.deleteAllHistory
        copy = binding.btnCopy
        share = binding.btnShare
        addToFavs = binding.btnAddtofavs

        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.setHasFixedSize(true)
        historyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this.context,
                DividerItemDecoration.VERTICAL
            )
        )

        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        mAdapter = HistoryAdapter(Constants.history, this)

//        binding.randomDream.text = random.dreamItem
//        binding.randomDefinition.text = random.dreamDefinition

        val dream = StringBuilder()
        dream.append(binding.randomDream.text)
        dream.append(": ")
        dream.append(binding.randomDefinition.text)
        dream.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")

        copy.setOnClickListener {
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("dream", dream)
            clipboard.setPrimaryClip(clip)
            Toasty.custom(requireActivity(), R.string.copied, R.drawable.ic_copy, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
        }

        share.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "$dream")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
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

            clearData.setOnClickListener {
                clearData.animate().rotationBy(360f).duration = 150
                Constants.history.clear()
                binding.tvEmptyHistory.visibility = View.VISIBLE
                historyRecyclerView.visibility = View.GONE
            }

//            addToFavs.setOnClickListener {
//                if (Constants.favourites.contains(random)) {
//                    Toasty.custom(requireActivity(), R.string.alreadyAdded, R.drawable.ic_attention,R.color.blue_700,Toast.LENGTH_SHORT,true, true).show()
//                } else {
//                    Constants.favourites.add(random)
//                    Toasty.custom(requireActivity(), R.string.addedToFavs, R.drawable.ic_star_full, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
//                }
//            }
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