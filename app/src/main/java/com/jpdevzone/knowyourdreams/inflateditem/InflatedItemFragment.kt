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
//    private lateinit var copy: ImageButton
//    private lateinit var share: ImageButton
//    private lateinit var addToFavs: ImageButton

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_FRAME, R.style.NoActionBarTheme)
//    }

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

        inflatedItemViewModel.navigateBack.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    InflatedItemFragmentDirections.actionInflatedItemFragmentToSearchFragment())
                inflatedItemViewModel.doneNavigating()
            }
        })

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)



//        copy = binding.btnCopy
//        share = binding.btnShare
//        addToFavs = binding.btnAddtofavs

//        dialog!!.let {
//
//            val mArgs = arguments
//            val myItem = mArgs!!.getString("Item")
//            val myDef = mArgs.getString("Definition")
//
//            binding.inflatedDream.text = myItem
//            binding.inflatedDefinition.text = myDef
//
////            val inflated = Dream(
//////                myItem!!,
//////                myDef!!
////            )
//
//            val dream = StringBuilder()
//            dream.append(binding.inflatedDream.text)
//            dream.append(": ")
//            dream.append(binding.inflatedDefinition.text)
//            dream.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")
//
//            copy.setOnClickListener {
//                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                val clip = ClipData.newPlainText("dream", dream)
//                clipboard.setPrimaryClip(clip)
//                Toasty.custom(requireActivity(), R.string.copied, R.drawable.ic_copy, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
//            }
//
//            share.setOnClickListener {
//                val shareIntent = Intent().apply {
//                    this.action = Intent.ACTION_SEND
//                    this.putExtra(Intent.EXTRA_TEXT, "$dream")
//                    this.type = "text/plain"
//                }
//                startActivity(shareIntent)
//            }
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
