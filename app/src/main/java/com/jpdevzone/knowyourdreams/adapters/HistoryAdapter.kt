package com.jpdevzone.knowyourdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class HistoryAdapter (private val history: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item_history)
        var definition: TextView = itemView.findViewById(R.id.tv_definition_history)
        var icon: CheckBox = itemView.findViewById(R.id.addToFavourites_history)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val item = history[position].dreamItem
            val definition = history[position].dreamDefinition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(item,definition)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.history_item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = history[position]
        viewHolder.dream.text = currentItem.dreamItem
        viewHolder.definition.text = currentItem.dreamDefinition
        viewHolder.icon.isChecked = currentItem.isChecked
        viewHolder.icon.isEnabled = false
    }

    interface OnItemClickListener {
        fun onItemClick(item: String, definition: String)
    }

    override fun getItemCount(): Int {
        return history.size
    }
}