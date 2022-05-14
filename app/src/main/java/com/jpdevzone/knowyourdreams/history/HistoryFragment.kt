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

        historyViewModel.history.observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }

        historyViewModel.navigateToHistoryData.observe(viewLifecycleOwner) { dreamId ->
            dreamId?.let {
                navigate(
                    HistoryFragmentDirections
                        .actionHistoryFragmentToInflatedItemFragment(dreamId)
                )
                historyViewModel.onDreamNavigated()
                historyViewModel.updateTimestampVisited(dreamId)
            }
        }

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
}