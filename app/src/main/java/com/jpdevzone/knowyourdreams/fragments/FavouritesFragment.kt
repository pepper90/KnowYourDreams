package com.jpdevzone.knowyourdreams.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.adapters.FavouritesAdapter
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment(), FavouritesAdapter.OnItemClickListener {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouritesRecyclerView: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerView.Adapter<*>

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

            favouritesRecyclerView = binding.favouritesRecyclerView
            favouritesRecyclerView.setHasFixedSize(true)
            favouritesRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )


        val random = Constants.dreamList.shuffled()[1]

        binding.randomDream.text = random.dreamItem
        binding.randomDefinition.text = random.dreamDefinition
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (Constants.favourites.isNotEmpty()) {
                binding.tvEmptyFavourites.visibility = View.GONE
                favouritesRecyclerView.visibility = View.VISIBLE
                mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                (mLayoutManager as LinearLayoutManager).stackFromEnd = true
                mAdapter = FavouritesAdapter(Constants.favourites, this)

                favouritesRecyclerView.layoutManager = mLayoutManager
                favouritesRecyclerView.adapter = mAdapter
            }else{
                binding.tvEmptyFavourites.visibility = View.VISIBLE
                favouritesRecyclerView.visibility = View.GONE
            }
        }
    }

    override fun onItemClick(item: String, definition: String, currentItem: Dream) {
        Constants.history.add(currentItem)
        println(Constants.history.size)
        val args = Bundle()
        args.putString("Item", item)
        args.putString("Definition", definition)

        val inflatedFragment = InflatedItemFragment()
        inflatedFragment.arguments = args
        val fm = requireActivity().supportFragmentManager

        inflatedFragment.show(fm, "inflatedItem")
    }
}
