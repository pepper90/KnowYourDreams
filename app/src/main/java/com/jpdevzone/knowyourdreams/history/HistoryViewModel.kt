package com.jpdevzone.knowyourdreams.history

import android.app.Application
import androidx.lifecycle.*
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao
import com.jpdevzone.knowyourdreams.randomInt2
import kotlinx.coroutines.*

class HistoryViewModel(
    val database: DreamDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val history = database.getHistory(true)

    private val _navigateToHistoryData = MutableLiveData<Int?>()
    val navigateToHistoryData: LiveData<Int?>
        get() = _navigateToHistoryData

    fun onDreamClicked(id: Int) {
        _navigateToHistoryData.value = id
    }

    fun onDreamNavigated() {
        _navigateToHistoryData.value = null
    }

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun deleteFromHistory(dream: Dream) {
        uiScope.launch {
            update(dream)
        }
    }

    private suspend fun update(dream: Dream) {
        withContext(Dispatchers.IO) {
            database.updateHistoryById(dream.id, false, System.currentTimeMillis())
        }
    }

    fun deleteAll() {
        uiScope.launch {
            updateAllToFalse()
        }
    }

    private suspend fun updateAllToFalse() {
        withContext(Dispatchers.IO) {
            database.updateAllHistoryToFalse(false)
        }
    }

    val historyIsNotEmpty = Transformations.map(history) {
        it.isNotEmpty()
    }

    private val randomDream = MediatorLiveData<Dream>()

    fun getRandomDream() = randomDream

    init {
        randomDream.addSource(database.get(randomInt2), randomDream::setValue)
    }

    fun updateRandomDream(id: Int, status: Boolean) {
        uiScope.launch {
            updateRandomChecked(id, status)
        }
    }

    private suspend fun updateRandomChecked(id: Int, status: Boolean) {
        withContext(Dispatchers.IO) {
            database.updateFavouritesById(id, status, System.currentTimeMillis())
        }
    }

    fun updateTimestampVisited(id: Int) {
        uiScope.launch {
            updateTimestamp(id)
        }
    }

    private suspend fun updateTimestamp(id: Int) {
        withContext(Dispatchers.IO) {
            database.updateVisitedAt(id, System.currentTimeMillis())
        }
    }
}