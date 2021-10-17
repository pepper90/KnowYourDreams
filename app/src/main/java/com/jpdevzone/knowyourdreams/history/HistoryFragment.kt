package com.jpdevzone.knowyourdreams.history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentHistoryBinding
import com.jpdevzone.knowyourdreams.stringBuilder
import es.dmoral.toasty.Toasty

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Architecture components________________________________________________

        val binding: FragmentHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DreamDatabase.getInstance(application).dreamDatabaseDao
        val viewModelFactory = HistoryViewModelFactory(dataSource, application)
        val historyViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.historyViewModel = historyViewModel
        binding.lifecycleOwner = this

        //HISTORY LIST____________________________________________________________
        val historyRecyclerView = binding.historyRecyclerView
        val historyAdapter = HistoryAdapter(
            HistoryClickListener {
                dreamId -> historyViewModel.onDreamClicked(dreamId)
            },
            DeleteFromHistoryListener {
                dream -> historyViewModel.deleteFromHistory(dream)
            }
        )
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        historyViewModel.history.observe(viewLifecycleOwner, {
            historyAdapter.submitList(it)
        })

        historyViewModel.navigateToHistoryData.observe(viewLifecycleOwner, {dreamId ->
            dreamId?.let {  navigate(
                HistoryFragmentDirections
                .actionHistoryFragmentToInflatedItemFragment(dreamId))
                historyViewModel.onDreamNavigated()
                historyViewModel.updateTimestampVisited(dreamId)
            }
        })

        //RANDOM DREAM___________________________________________________________

        binding.btnCopy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("dream",
                stringBuilder(
                    binding.randomDream.text.toString(),
                    binding.randomDefinition.text.toString()
                )
            )
            clipboard.setPrimaryClip(clip)
            Toasty.custom(requireContext(), R.string.copied, R.drawable.ic_copy, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(
                    Intent.EXTRA_TEXT,
                    stringBuilder(
                        binding.randomDream.text.toString(),
                        binding.randomDefinition.text.toString()
                    )
                )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        return binding.root
    }

    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        clearData = binding.deleteAllHistory
//        copy = binding.btnCopy
//        share = binding.btnShare
//        addToFavs = binding.btnAddtofavs
//
//        historyRecyclerView = binding.historyRecyclerView
//        historyRecyclerView.setHasFixedSize(true)
//        historyRecyclerView.addItemDecoration(
//            DividerItemDecoration(
//                this.context,
//                DividerItemDecoration.VERTICAL
//            )
//        )

//        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//        mAdapter = HistoryAdapter(Constants.history)

//        binding.randomDream.text = random.dreamItem
//        binding.randomDefinition.text = random.dreamDefinition

//        val dream = StringBuilder()
//        dream.append(binding.randomDream.text)
//        dream.append(": ")
//        dream.append(binding.randomDefinition.text)
//        dream.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")
//
//        copy.setOnClickListener {
//            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clip = ClipData.newPlainText("dream", dream)
//            clipboard.setPrimaryClip(clip)
//            Toasty.custom(requireActivity(), R.string.copied, R.drawable.ic_copy, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
//        }
//
//        share.setOnClickListener {
//            val shareIntent = Intent().apply {
//                this.action = Intent.ACTION_SEND
//                this.putExtra(Intent.EXTRA_TEXT, "$dream")
//                this.type = "text/plain"
//            }
//            startActivity(shareIntent)
//        }
//    }

//    @SuppressLint("NotifyDataSetChanged")
//    override fun onHiddenChanged(hidden: Boolean) {
//        super.onHiddenChanged(hidden)
//        if (!hidden) {
//            if (Constants.history.isNotEmpty()) {
//                binding.tvEmptyHistory.visibility = View.GONE
//                historyRecyclerView.visibility = View.VISIBLE
//
//                mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
//                mAdapter = if (Constants.history.size == 1) {
//                    HistoryAdapter(Constants.history, this)
//                }else{
//                    HistoryAdapter(Constants.history.reversed() as ArrayList<Dream>,this)
//                }
//
//                historyRecyclerView.layoutManager = mLayoutManager
//                historyRecyclerView.adapter = mAdapter
//            } else {
//                binding.tvEmptyHistory.visibility = View.VISIBLE
//                historyRecyclerView.visibility = View.GONE
//            }
//
//            clearData.setOnClickListener {
//                clearData.animate().rotationBy(360f).duration = 150
//                Constants.history.clear()
//                binding.tvEmptyHistory.visibility = View.VISIBLE
//                historyRecyclerView.visibility = View.GONE
//            }

//            addToFavs.setOnClickListener {
//                if (Constants.favourites.contains(random)) {
//                    Toasty.custom(requireActivity(), R.string.alreadyAdded, R.drawable.ic_attention,R.color.blue_700,Toast.LENGTH_SHORT,true, true).show()
//                } else {
//                    Constants.favourites.add(random)
//                    Toasty.custom(requireActivity(), R.string.addedToFavs, R.drawable.ic_star_full, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
//                }
//            }
//        }
//    }

//    override fun onItemClick(item: String, definition: String) {
//        val args = Bundle()
//        args.putString("Item", item)
//        args.putString("Definition", definition)

//        val inflatedFragment = InflatedItemFragment()
//        inflatedFragment.arguments = args
//        val fm = requireActivity().supportFragmentManager
//
//        inflatedFragment.show(fm, "inflatedItem")
//    }
}