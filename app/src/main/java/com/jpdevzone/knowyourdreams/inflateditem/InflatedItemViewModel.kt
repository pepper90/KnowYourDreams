package com.jpdevzone.knowyourdreams.inflateditem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao

class InflatedItemViewModel(dreamId: Int, dataSource: DreamDatabaseDao) : ViewModel() {
    val database = dataSource

    private val dream = MediatorLiveData<Dream>()

    fun getDream() = dream

    init {
        dream.addSource(database.get(dreamId), dream::setValue)
    }

    private val _navigateBack = MutableLiveData<Boolean?>()
    val navigateBack: LiveData<Boolean?>
        get() = _navigateBack

    fun doneNavigating() {
        _navigateBack.value = null
    }

    fun stringBuilder(dreamItem: String, dreamDefinition: String) : CharSequence {
        val data = StringBuilder()
        data.append(dreamItem)
        data.append(": ")
        data.append(dreamDefinition)
        data.append("\n\nКопирано от СъновникБГ - тълкуване на сънища / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.knowyourdreams")
        return data
    }
}