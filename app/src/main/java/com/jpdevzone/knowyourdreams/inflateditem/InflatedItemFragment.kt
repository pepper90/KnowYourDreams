package com.jpdevzone.knowyourdreams.inflateditem

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
import androidx.navigation.fragment.findNavController
import com.google.android.play.core.review.ReviewManagerFactory
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentInflatedItemBinding
import com.jpdevzone.knowyourdreams.stringBuilder
import es.dmoral.toasty.Toasty

class InflatedItemFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding : FragmentInflatedItemBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_inflated_item, container,false)

        val application = requireNotNull(this.activity).application
        val arguments = InflatedItemFragmentArgs.fromBundle(requireArguments())

        val dataSource = DreamDatabase.getInstance(application).dreamDatabaseDao
        val viewModelFactory = InflatedItemViewModelFactory(arguments.dreamId, dataSource)

        val inflatedItemViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(InflatedItemViewModel::class.java)

        binding.inflatedItemViewModel = inflatedItemViewModel

        binding.lifecycleOwner = this

        binding.btnCopy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("dream",
                stringBuilder(
                    binding.inflatedDream.text.toString(),
                    binding.inflatedDefinition.text.toString()
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
                        binding.inflatedDream.text.toString(),
                        binding.inflatedDefinition.text.toString()
                    )
                )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        inflatedItemViewModel.navigateToSearchFragment.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigateUp()
                inflatedItemViewModel.doneNavigatingToSearchFragment()
            }
        }

        inflatedItemViewModel.navigateToFavouritesFragment.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigateUp()
                inflatedItemViewModel.doneNavigatingToFavouritesFragment()
            }
        }

        inflatedItemViewModel.navigateToHistoryFragment.observe(viewLifecycleOwner) {
            if (it == true) {
                this.findNavController().navigateUp()
                inflatedItemViewModel.doneNavigatingToHistoryFragment()
            }
        }

        return binding.root
    }
}
