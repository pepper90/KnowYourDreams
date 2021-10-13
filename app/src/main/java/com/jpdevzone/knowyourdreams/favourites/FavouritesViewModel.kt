package com.jpdevzone.knowyourdreams.favourites

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao
import kotlinx.coroutines.*
import kotlin.random.Random

class FavouritesViewModel(
    val database: DreamDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.i("FavouritesViewModel", "FavouritesViewModel destroyed!")
    }

    val favourites = database.getOnlyChecked(true)

    private val _navigateToFavouritesData = MutableLiveData<Int?>()
    val navigateToFavouritesData: LiveData<Int?>
        get() = _navigateToFavouritesData

    fun onDreamClicked(id: Int) {
        _navigateToFavouritesData.value = id
    }

    fun onDreamNavigated() {
        _navigateToFavouritesData.value = null
    }

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateToFalse(dream: Dream) {
        uiScope.launch {
            update(dream)
        }
    }

    private suspend fun update(dream: Dream) {
        withContext(Dispatchers.IO) {
            database.updateById(dream.id, false)
            println(dream.isChecked)
        }
    }

    fun deleteAll() {
        uiScope.launch {
            updateAllToFalse()
        }
    }

    private suspend fun updateAllToFalse() {
        withContext(Dispatchers.IO) {
            database.updateAllToFalse(false)
        }
    }

    val favouritesIsNotEmpty = Transformations.map(favourites) {
        it.isNotEmpty()
    }

    private val randomInt = Random.nextInt(4444)+1

    private val randomDream = MediatorLiveData<Dream>()

    fun getRandomDream() = randomDream

    init {
        randomDream.addSource(database.get(randomInt), randomDream::setValue)
    }

    fun updateRandomDream(id: Int, status: Boolean) {
        uiScope.launch {
            updateRandomChecked(id, status)
            println(database.get(id))
        }
    }

    private suspend fun updateRandomChecked(id: Int, status: Boolean) {
        withContext(Dispatchers.IO) {
            database.updateById(id, status)
        }
    }
}