package com.jpdevzone.knowyourdreams.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class HomeAdapter(private val searched: ArrayList<Dream>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var dream: TextView = view.findViewById(R.id.tv_item)
        var definition: TextView = view.findViewById(R.id.tv_definition)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.home_item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dream.text = searched[position].dreamItem
        viewHolder.definition.text = searched[position].dreamDefinition
        viewHolder.itemView.setOnClickListener {
            Log.i("Clicked item","$position")
        }
    }

    override fun getItemCount(): Int {
        val limit = 5
        return if(searched.size > limit){
            limit
        } else {
            searched.size
        }
    }
}