package com.jpdevzone.knowyourdreams.favourites

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jpdevzone.knowyourdreams.database.Dream

@BindingAdapter("favouritesItem")
fun TextView.favouritesItem(item: Dream?) {
    text = item?.dreamItem
}

@BindingAdapter("favouritesDefinition")
fun TextView.favouritesDefinition(item: Dream?) {
    text = item?.dreamDefinition
}