package com.jpdevzone.knowyourdreams.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.databinding.ItemLetterBinding

class AlphabetAdapter(private val clickListener: LetterClickListener) : ListAdapter<Letter, AlphabetAdapter.ViewHolder>(LetterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemLetterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Letter, clickListener: LetterClickListener) {
            binding.letter = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLetterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class LetterDiffCallback: DiffUtil.ItemCallback<Letter>() {
        override fun areItemsTheSame(oldItem: Letter, newItem: Letter): Boolean {
            return oldItem.letter == newItem.letter
        }

        override fun areContentsTheSame(oldItem: Letter, newItem: Letter): Boolean {
            return oldItem == newItem
        }
    }
}

class LetterClickListener(val clickListener: (letter: String) -> Unit) {
    fun onClick(letter: Letter) = clickListener(letter.letter)
}