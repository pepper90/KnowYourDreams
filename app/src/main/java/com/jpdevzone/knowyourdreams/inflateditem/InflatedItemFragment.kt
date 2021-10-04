package com.jpdevzone.knowyourdreams.inflateditem

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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentInflatedItemBinding
import es.dmoral.toasty.Toasty

class InflatedItemFragment: DialogFragment() {

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

        val copy = binding.btnCopy
        val share = binding.btnShare
        val addToFavs = binding.btnAddtofavs

        copy.setOnClickListener {
                val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("dream",
                    inflatedItemViewModel.stringBuilder(
                        binding.inflatedDream.text.toString(),
                        binding.inflatedDefinition.text.toString()
                    )
                )
                clipboard.setPrimaryClip(clip)
                Toasty.custom(requireContext(), R.string.copied, R.drawable.ic_copy, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
            }

        share.setOnClickListener {
                val shareIntent = Intent().apply {
                    this.action = Intent.ACTION_SEND
                    this.putExtra(
                        Intent.EXTRA_TEXT,
                        inflatedItemViewModel.stringBuilder(
                            binding.inflatedDream.text.toString(),
                            binding.inflatedDefinition.text.toString()
                        )
                    )
                    this.type = "text/plain"
                }
                startActivity(shareIntent)
            }

        addToFavs.setOnClickListener {
            addToFavs.setImageResource(R.drawable.ic_inflated_star_full)
        }

        inflatedItemViewModel.navigateBack.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    InflatedItemFragmentDirections.actionInflatedItemFragmentToSearchFragment())
                inflatedItemViewModel.doneNavigating()
            }
        })

        return binding.root
    }

//        addToFavs = binding.btnAddtofavs

//
//
////            addToFavs.setOnClickListener {
////                if (Constants.favourites.contains(inflated)) {
////                    Toasty.custom(requireActivity(), R.string.alreadyAdded, R.drawable.ic_attention,R.color.blue_700,Toast.LENGTH_SHORT,true, true).show()
////                } else {
////                    Constants.favourites.add(inflated)
////                    Toasty.custom(requireActivity(), R.string.addedToFavs, R.drawable.ic_star_full, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
////                }
////            }
//        }
//    }
}
