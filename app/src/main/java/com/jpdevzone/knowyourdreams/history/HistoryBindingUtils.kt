package com.jpdevzone.knowyourdreams.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jpdevzone.knowyourdreams.database.Dream

@BindingAdapter("historyItem")
fun TextView.historyItem(item: Dream?) {
    text = item?.dreamItem
}

@BindingAdapter("historyDefinition")
fun TextView.historyDefinition(item: Dream?) {
    text = item?.dreamDefinition
}