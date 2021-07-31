package com.jpdevzone.knowyourdreams.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class HistoryAdapter(private val history: ArrayList<Dream>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var dream: TextView = view.findViewById(R.id.tv_item_history)
        var definition: TextView = view.findViewById(R.id.tv_definition_history)
        var icon: CheckBox = view.findViewById(R.id.addToFavourites_history)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.history_item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dream.text = history[position].dreamItem
        viewHolder.definition.text = history[position].dreamDefinition
        viewHolder.itemView.setOnClickListener {
            Log.i("Clicked item","$position")
        }
    }

    override fun getItemCount(): Int {
        return history.size
    }
}