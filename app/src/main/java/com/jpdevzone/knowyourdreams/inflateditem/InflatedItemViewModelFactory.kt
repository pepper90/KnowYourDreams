package com.jpdevzone.knowyourdreams.inflateditem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao

class InflatedItemViewModelFactory(
    private val dreamId: Int,
    private val dataSource: DreamDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InflatedItemViewModel::class.java)) {
            return InflatedItemViewModel(dreamId, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}