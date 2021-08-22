package com.jpdevzone.knowyourdreams.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.databinding.FragmentInflatedItemBinding
import es.dmoral.toasty.Toasty

class InflatedItemFragment: DialogFragment() {
    private lateinit var binding: FragmentInflatedItemBinding
    private lateinit var copy: ImageButton
    private lateinit var share: ImageButton
    private lateinit var addToFavs: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.NoActionBarTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInflatedItemBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        copy = binding.btnCopy
        share = binding.btnShare
        addToFavs = binding.btnAddtofavs

        dialog!!.let {

            val mArgs = arguments
            val myItem = mArgs!!.getString("Item")
            val myDef = mArgs.getString("Definition")

            binding.inflatedDream.text = myItem
            binding.inflatedDefinition.text = myDef

            val inflated = Dream(
                myItem!!,
                myDef!!
            )

            val dream = StringBuilder()
            dream.append(binding.inflatedDream.text)
            dream.append(": ")
            dream.append(binding.inflatedDefinition.text)
            dream.append("\n\nКопирано от БГ Съновник / Google Play: Link")

            copy.setOnClickListener {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
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

            addToFavs.setOnClickListener {
                if (Constants.favourites.contains(inflated)) {
                    Toasty.custom(requireActivity(), R.string.alreadyAdded, R.drawable.ic_attention,R.color.blue_700,Toast.LENGTH_SHORT,true, true).show()
                } else {
                    Constants.favourites.add(inflated)
                    Toasty.custom(requireActivity(), R.string.addedToFavs, R.drawable.ic_star_full, R.color.blue_700, Toast.LENGTH_SHORT, true, true).show()
                }
            }
        }
    }
}
