package com.jpdevzone.knowyourdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class RecyclerViewAdapter(private val dreams: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Filterable {

    private var tempDreams = ArrayList<Dream>()

    init {
        tempDreams = dreams
    }

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val item = tempDreams[position].dreamItem
            val definition = tempDreams[position].dreamDefinition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position,item,definition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dream.text = tempDreams[position].dreamItem
    }

    override fun getItemCount(): Int {
        return tempDreams.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: String, definition: String)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                tempDreams = if (constraint==null || constraint.isEmpty()) {
                    dreams
                } else {
                    val resultDreams = ArrayList<Dream>()
                    for (row in dreams) {
                        if (row.dreamItem.lowercase().startsWith(constraint.toString().lowercase().trim())) {
                            resultDreams.add(row)
                        }
                    }
                    resultDreams
                }
                val filterResults = FilterResults()
                filterResults.values = tempDreams
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                tempDreams = results?.values as ArrayList<Dream>
                notifyDataSetChanged()
            }

        }
    }


}