package com.jpdevzone.knowyourdreams.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val searchView = activity?.findViewById<SearchView>(R.id.searchView)
        searchView?.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }
}