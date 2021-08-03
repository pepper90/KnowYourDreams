package com.jpdevzone.knowyourdreams.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.adapters.FavouritesAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment(), FavouritesAdapter.OnItemClickListener {
    private lateinit var binding: FragmentFavouritesBinding
    private val favourites = Constants.favourites
    private val history = Constants.history


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
            val mAdapter: RecyclerView.Adapter<*> = FavouritesAdapter(favourites, this)

            favouritesRecyclerView.layoutManager = mLayoutManager
            favouritesRecyclerView.adapter = mAdapter
        }
    }

    private fun history(currentItem: Dream): ArrayList<Dream> {
        val limit = 4
        if (history.size > limit) {
            history.add(currentItem)
            history.remove(history[0])
        } else {
            history.add(currentItem)
        }
        return history
    }

    override fun onItemClick(item: String, definition: String, currentItem: Dream) {
        history(currentItem)
        println(history.size)
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }
}
