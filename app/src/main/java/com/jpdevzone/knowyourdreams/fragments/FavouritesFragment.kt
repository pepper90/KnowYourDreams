package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.adapters.FavouritesAdapter
import com.jpdevzone.knowyourdreams.adapters.HistoryAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val favourites = Constants.favourites


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val random = Constants.getDreams().shuffled()[1]

        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (favourites.size == 0) {
                binding.tvEmptyFavourites.visibility = View.VISIBLE
            } else {
                binding.tvEmptyFavourites.visibility = View.GONE
            }
            val favouritesRecyclerView: RecyclerView = binding.favouritesRecyclerView
            favouritesRecyclerView.setHasFixedSize(true)
            val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,true)
            val mAdapter: RecyclerView.Adapter<*> = FavouritesAdapter(favourites)

            favouritesRecyclerView.layoutManager = mLayoutManager
            favouritesRecyclerView.adapter = mAdapter
        }
    }
}
