package com.jpdevzone.knowyourdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class FavouritesAdapter (private val favourites: ArrayList<Dream>) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dream: TextView = itemView.findViewById(R.id.tv_item_favourites)
        var definition: TextView = itemView.findViewById(R.id.tv_definition_favourites)
        var icon: ImageView = itemView.findViewById(R.id.delete_from_favourites)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.favs_item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val currentItem = favourites[position]
        viewHolder.dream.text = currentItem.dreamItem
        viewHolder.definition.text = currentItem.dreamDefinition
        viewHolder.icon.setOnClickListener{
            println(favourites.size)
        }
    }

    override fun getItemCount(): Int {
        return favourites.size
    }
}