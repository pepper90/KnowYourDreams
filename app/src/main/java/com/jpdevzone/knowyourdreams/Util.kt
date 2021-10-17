package com.jpdevzone.knowyourdreams

import kotlin.random.Random

fun stringBuilder(dreamItem: String, dreamDefinition: String) : CharSequence {
    val data = StringBuilder()
    data.append(dreamItem)
    data.append(": ")
    data.append(dreamDefinition)
    data.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")
    return data
}

val randomInt = Random.nextInt(4444)+1

val randomInt2 = Random.nextInt(4444)+1