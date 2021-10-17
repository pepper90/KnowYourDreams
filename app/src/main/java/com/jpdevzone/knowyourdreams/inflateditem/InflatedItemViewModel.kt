package com.jpdevzone.knowyourdreams.inflateditem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao
import kotlinx.coroutines.*

class InflatedItemViewModel(dreamId: Int, dataSource: DreamDatabaseDao) : ViewModel() {
    val database = dataSource

    private val dream = MediatorLiveData<Dream>()

    fun getDream() = dream

    init {
        dream.addSource(database.get(dreamId), dream::setValue)
    }

    private val _navigateToSearchFragment = MutableLiveData<Boolean?>()
    val navigateToSearchFragment: LiveData<Boolean?>
        get() = _navigateToSearchFragment

    fun doneNavigatingToSearchFragment() {
        _navigateToSearchFragment.value = null
    }


    private val _navigateToFavouritesFragment = MutableLiveData<Boolean?>()
    val navigateToFavouritesFragment: LiveData<Boolean?>
        get() = _navigateToFavouritesFragment

    fun doneNavigatingToFavouritesFragment() {
        _navigateToFavouritesFragment.value = null
    }

    private val _navigateToHistoryFragment = MutableLiveData<Boolean?>()
    val navigateToHistoryFragment: LiveData<Boolean?>
        get() = _navigateToHistoryFragment

    fun doneNavigatingToHistoryFragment() {
        _navigateToHistoryFragment.value = null
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateChecked(id: Int, status: Boolean) {
        uiScope.launch {
            update(id, status)
        }
    }

    private suspend fun update(id: Int, status: Boolean) {
        withContext(Dispatchers.IO) {
            database.updateFavouritesById(id, status, System.currentTimeMillis())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}