package com.jpdevzone.knowyourdreams

data class Dream(
    var id: Int,
    var dreamItem: String,
    var dreamDefinition: String,
    var isChecked: Boolean = false

)
