package com.jpdevzone.knowyourdreams.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jpdevzone.knowyourdreams.databinding.FragmentInflatedItemBinding

class InflatedItemFragment: DialogFragment() {
    private lateinit var binding: FragmentInflatedItemBinding

    override fun onStart() {
        super.onStart()
        dialog!!.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            it.window!!.setLayout(width, height)

            val mArgs = arguments
            val myItem = mArgs!!.getString("Item")
            val myDef = mArgs.getString("Definition")

            binding.inflatedDream.text = myItem
            binding.inflatedDefinition.text = myDef
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInflatedItemBinding.inflate(inflater,container,false)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }
}
