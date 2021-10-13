package com.jpdevzone.knowyourdreams.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.databinding.FavsItemDreamBinding

class FavouritesAdapter (
    private val clickListener: FavouritesClickListener,
    private val setToFalseListener: SetToFalseListener
    ): ListAdapter<Dream, FavouritesAdapter.ViewHolder>(DreamDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, setToFalseListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (private val binding: FavsItemDreamBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind (
            item: Dream,
            clickListener: FavouritesClickListener,
            setToFalseListener: SetToFalseListener
        ) {
            binding.dream = item
            binding.clickListener = clickListener
            binding.setToFalseListener = setToFalseListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavsItemDreamBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DreamDiffCallback: DiffUtil.ItemCallback<Dream>() {
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.isChecked == newItem.isChecked
        }

    }
}

class FavouritesClickListener(val clickListener: (dreamId: Int) -> Unit) {
    fun onClick(dream: Dream) = clickListener(dream.id)
}

class SetToFalseListener(val setToFalseListener: (dream: Dream) -> Unit) {
    fun onClick(dream: Dream) = setToFalseListener(dream)
}