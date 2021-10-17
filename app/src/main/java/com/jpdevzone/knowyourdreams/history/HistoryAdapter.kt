package com.jpdevzone.knowyourdreams.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.databinding.HistoryItemDreamBinding

class HistoryAdapter (val clickListener: HistoryClickListener, val deleteFromHistoyListener: DeleteFromHistoryListener) : ListAdapter<Dream, HistoryAdapter.ViewHolder>(DreamDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteFromHistoyListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: HistoryItemDreamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Dream,
            clickListener: HistoryClickListener,
            deleteFromHistoyListener: DeleteFromHistoryListener
        ) {
            binding.dream = item
            binding.clickListener = clickListener
            binding.deleteFromHistoyListener = deleteFromHistoyListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HistoryItemDreamBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DreamDiffCallback : DiffUtil.ItemCallback<Dream>() {
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.isChecked == newItem.isChecked
        }

    }
}

class HistoryClickListener(val clickListener: (dreamId: Int) -> Unit) {
    fun onClick(dream: Dream) = clickListener(dream.id)
}

class DeleteFromHistoryListener(val deleteFromHistoyListener: (dream: Dream) -> Unit) {
    fun onClick(dream: Dream) = deleteFromHistoyListener(dream)
}

