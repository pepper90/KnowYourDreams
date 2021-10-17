package com.jpdevzone.knowyourdreams.favourites

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
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.jpdevzone.knowyourdreams.R
import com.jpdevzone.knowyourdreams.database.DreamDatabase
import com.jpdevzone.knowyourdreams.databinding.FragmentFavouritesBinding
import com.jpdevzone.knowyourdreams.stringBuilder
import es.dmoral.toasty.Toasty

class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Architecture components________________________________________________

        val binding: FragmentFavouritesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourites, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DreamDatabase.getInstance(application).dreamDatabaseDao
        val viewModelFactory = FavouritesViewModelFactory(dataSource, application)
        val favouritesViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(FavouritesViewModel::class.java)

        binding.favouritesViewModel = favouritesViewModel
        binding.lifecycleOwner = this

        //FAVOURITES LIST________________________________________________________

        val favouritesRecyclerView = binding.favouritesRecyclerView
        val favouritesAdapter = FavouritesAdapter(
            FavouritesClickListener {
                    dreamId -> favouritesViewModel.onDreamClicked(dreamId)
            },
            DreamCheckListener {
                    dream ->  favouritesViewModel.updateChecked(dream)}
        )
        favouritesRecyclerView.adapter = favouritesAdapter
        favouritesRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        favouritesViewModel.favourites.observe(viewLifecycleOwner, {
            favouritesAdapter.submitList(it)
        })

        favouritesViewModel.navigateToFavouritesData.observe(viewLifecycleOwner, {dreamId ->
            dreamId?.let {  navigate(
                FavouritesFragmentDirections
                .actionFavouritesFragmentToInflatedItemFragment(dreamId))
                favouritesViewModel.onDreamNavigated()
                favouritesViewModel.addToHistory(dreamId)
            }
        })

        //RANDOM DREAM___________________________________________________________

        binding.btnCopy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("dream",
                stringBuilder(
                    binding.randomDream.text.toString(),
                    binding.randomDefinition.text.toString()
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
                        binding.randomDream.text.toString(),
                        binding.randomDefinition.text.toString()
                    )
                )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }

        return binding.root
    }

    private fun navigate(destination: NavDirections) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }
}
