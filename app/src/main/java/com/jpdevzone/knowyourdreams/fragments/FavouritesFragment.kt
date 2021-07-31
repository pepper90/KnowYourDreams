package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }
}