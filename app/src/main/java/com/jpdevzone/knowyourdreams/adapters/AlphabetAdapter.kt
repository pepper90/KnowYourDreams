package com.jpdevzone.knowyourdreams.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.R

class AlphabetAdapter (private val alphabet: ArrayList<String>, private val letterListener: OnLetterClickListener) : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvLetter: TextView = itemView.findViewById(R.id.tv_letter)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            val letter: String = tvLetter.text as String
            if (position != RecyclerView.NO_POSITION) {
                letterListener.onLetterClick(letter)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_letter,viewGroup,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvLetter.text = alphabet[position]
    }

    override fun getItemCount(): Int {
        return alphabet.size
    }

    interface OnLetterClickListener {
        fun onLetterClick(letter: String)
    }
}