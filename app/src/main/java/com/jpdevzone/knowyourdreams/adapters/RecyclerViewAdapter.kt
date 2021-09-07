package com.jpdevzone.knowyourdreams.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class RecyclerViewAdapter(private var dreams: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Filterable {
    private var dreamsFull = ArrayList<Dream>()

    init {
        dreamsFull = ArrayList(dreams)
    }


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val currentItem = dreams[position]
            val item = currentItem.dreamItem
            val definition = currentItem.dreamDefinition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(item,definition,currentItem)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = dreams[position]
        viewHolder.dream.text = currentItem.dreamItem
    }

    override fun getItemCount() = dreams.size

    interface OnItemClickListener {
        fun onItemClick(item: String, definition: String, currentItem: Dream)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredDreams = ArrayList<Dream>()

                if (constraint==null || constraint.isEmpty()) {
                    filteredDreams.addAll(dreamsFull)
                    notifyItemRangeChanged(0, dreams.size)
                } else {

                    for (row in dreamsFull) {
                        if (row.dreamItem.lowercase()
                                .startsWith(constraint.toString().lowercase().trim())
                        ) {
                            filteredDreams.add(row)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredDreams

                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                dreams.clear()
                dreams.addAll(results.values as ArrayList<Dream>)

                if(dreams.size > 0) {
                    notifyItemRangeChanged(0, dreams.size)
                    notifyDataSetChanged()
                }
            }
        }
    }
}