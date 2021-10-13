package com.jpdevzone.knowyourdreams

fun stringBuilder(dreamItem: String, dreamDefinition: String) : CharSequence {
    val data = StringBuilder()
    data.append(dreamItem)
    data.append(": ")
    data.append(dreamDefinition)
    data.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")
    return data
}