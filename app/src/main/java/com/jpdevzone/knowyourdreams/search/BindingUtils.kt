package com.jpdevzone.knowyourdreams.search

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jpdevzone.knowyourdreams.database.Dream

@BindingAdapter("dreamItem")
fun TextView.dreamItem(item: Dream?) {
    text = item?.dreamItem
}

@BindingAdapter("dreamDefinition")
fun TextView.dreamDefinition(item: Dream?) {
    text = item?.dreamDefinition
}

@BindingAdapter("letter")
fun TextView.letter(item: Letter?) {
    text = item?.letter
}

