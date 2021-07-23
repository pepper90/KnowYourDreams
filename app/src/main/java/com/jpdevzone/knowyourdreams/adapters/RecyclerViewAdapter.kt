package com.jpdevzone.knowyourdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class RecyclerViewAdapter(private val dreams: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val item = dreams[position].dreamItem
            val definition = dreams[position].dreamDefinition
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
        viewHolder.dream.text = dreams[position].dreamItem
    }

    override fun getItemCount(): Int {
        return dreams.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: String, definition: String)
    }
}