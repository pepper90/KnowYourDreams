package com.jpdevzone.knowyourdreams.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.databinding.ItemDreamBinding

class SearchAdapter(private val clickListener: DreamClickListener, private val checkListener: DreamCheckListener): ListAdapter<Dream, SearchAdapter.ViewHolder>(DreamDiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, checkListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor (private val binding: ItemDreamBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dream, clickListener: DreamClickListener, checkListener: DreamCheckListener) {
            binding.dream = item
            binding.clickListener = clickListener
            binding.checkListener = checkListener
            binding.executePendingBindings()
//            binding.addToFavourites.setOnCheckedChangeListener(null)
//            item.isChecked = binding.addToFavourites.isChecked
//            binding.addToFavourites.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
//                item.isChecked = checked
//                println(item)
//            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDreamBinding.inflate(layoutInflater, parent, false)
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

class DreamClickListener(val clickListener: (dreamId: Int) -> Unit) {
    fun onClick(dream: Dream) = clickListener(dream.id)
}

class DreamCheckListener(val checkListener: (dream: Dream) -> Unit) {
    fun onCheck(dream: Dream) = checkListener(dream)
}
//    private var dreamsFull = ArrayList<Dream>()
//
//    init {
//        dreamsFull = ArrayList(dreams)
//    }
//
//
//    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
//        var dream: TextView = itemView.findViewById(R.id.tv_item)
//
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View?) {
//            val position: Int = absoluteAdapterPosition
//            val currentItem = dreams[position]
//            val item = currentItem.dreamItem
//            val definition = currentItem.dreamDefinition
//            if (position != RecyclerView.NO_POSITION) {
//                listener.onItemClick(item,definition,currentItem)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dream,viewGroup,false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val currentItem = dreams[position]
//        viewHolder.dream.text = currentItem.dreamItem
//    }
//
//    override fun getItemCount() = dreams.size
//
//    interface OnItemClickListener {
//        fun onItemClick(item: String, definition: String, currentItem: Dream)
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filteredDreams = ArrayList<Dream>()
//
//                if (constraint==null || constraint.isEmpty()) {
//                    filteredDreams.addAll(dreamsFull)
//                    notifyItemRangeChanged(0, dreams.size)
//                } else {
//
//                    for (row in dreamsFull) {
//                        if (row.dreamItem.lowercase()
//                                .startsWith(constraint.toString().lowercase().trim())
//                        ) {
//                            filteredDreams.add(row)
//                        }
//                    }
//                }
//
//                val results = FilterResults()
//                results.values = filteredDreams
//
//                return results
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
//                dreams.clear()
//                dreams.addAll(results.values as ArrayList<Dream>)
//
//                if(dreams.size > 0) {
//                    notifyItemRangeChanged(0, dreams.size)
//                    notifyDataSetChanged()
//                }
//            }
//        }
//    }
