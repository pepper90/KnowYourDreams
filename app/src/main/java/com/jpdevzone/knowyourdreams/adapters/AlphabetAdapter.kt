package com.jpdevzone.knowyourdreams.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.R

class AlphabetAdapter (private val alphabet: ArrayList<String>) : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        var tvLetter: TextView = view.findViewById(R.id.tv_letter)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_letter,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvLetter.text = alphabet[position]
        viewHolder.itemView.setOnClickListener {
            Log.i("Clicked item","$position")
        }
    }

    override fun getItemCount(): Int {
        return alphabet.size
    }
}