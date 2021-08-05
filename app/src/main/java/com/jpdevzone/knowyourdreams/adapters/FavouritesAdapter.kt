package com.jpdevzone.knowyourdreams.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Constants
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class FavouritesAdapter (private val favourites: ArrayList<Dream>, private val listener: OnItemClickListener) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    private val dreams = Constants.getDreams()

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var dream: TextView = itemView.findViewById(R.id.tv_item_favourites)
        var definition: TextView = itemView.findViewById(R.id.tv_definition_favourites)
        var icon: CheckBox = itemView.findViewById(R.id.delete_from_favourites)
        var id: Int = itemView.id

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
        viewHolder.id = currentItem.id

        viewHolder.icon.setOnCheckedChangeListener(null)
        viewHolder.icon.isChecked = currentItem.isChecked

        if (position != RecyclerView.NO_POSITION) {
            viewHolder.icon.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
                currentItem.isChecked = checked
                if (!currentItem.isChecked) {
                    dreams[position].id = currentItem.id
                    dreams[position].isChecked = false
                    favourites.remove(currentItem)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
                println(favourites.size)
                println(dreams[position].id)
                println(dreams[position].isChecked)
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