package com.jpdevzone.knowyourdreams.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.Dream
import com.jpdevzone.knowyourdreams.R

class RecyclerViewAdapter(private val dreams: ArrayList<Dream>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var dream: TextView = view.findViewById(R.id.tv_item)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_dream,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dream.text = dreams[position].dreamItem
        viewHolder.itemView.setOnClickListener {
            Log.i("Clicked item","$position")
        }
    }

    override fun getItemCount(): Int {
        return dreams.size
    }
}