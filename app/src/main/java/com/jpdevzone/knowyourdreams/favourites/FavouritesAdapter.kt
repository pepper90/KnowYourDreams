package com.jpdevzone.knowyourdreams.favourites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.R

class FavouritesAdapter (private val favourites: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {


    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item_favourites)
        var definition: TextView = itemView.findViewById(R.id.tv_definition_favourites)
        var icon: ImageButton = itemView.findViewById(R.id.delete_from_favourites)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            val currentItem = favourites[position]
            val item = favourites[position].dreamItem
            val definition = favourites[position].dreamDefinition

            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(item,definition,currentItem)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.favs_item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = favourites[position]
        viewHolder.dream.text = currentItem.dreamItem
        viewHolder.definition.text = currentItem.dreamDefinition


        if (position != RecyclerView.NO_POSITION) {
            viewHolder.icon.setOnClickListener {
                favourites.remove(currentItem)
                notifyItemRemoved(position)
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: String, definition: String, currentItem: Dream)
    }

    override fun getItemCount(): Int {
        return favourites.size
    }
}