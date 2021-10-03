package com.jpdevzone.knowyourdreams.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jpdevzone.knowyourdreams.database.Dream
import com.jpdevzone.knowyourdreams.database.DreamDatabaseDao
import kotlinx.coroutines.*

class SearchViewModel(
    val database: DreamDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.i("SearchViewModel", "SearchViewModel destroyed!")
    }

    fun alphabet(): List<Letter> {
        val list = ArrayList<Letter>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add(Letter(i.toString()))
        }
        return list
    }

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun updateChecked(dream: Dream) {
        uiScope.launch {
            update(dream)
        }
    }

    private suspend fun update(dream: Dream) {
        withContext(Dispatchers.IO) {
        database.update(dream)
        println(dream.isChecked)
        }
    }

    val dreams = database.getAllDrams()

    fun searchDatabase(searchQuery: String): LiveData<List<Dream>> {
        return database.searchDreams(searchQuery)
    }

    private val _navigateToDreamData = MutableLiveData<Int?>()
    val navigateToDreamData: LiveData<Int?>
        get() = _navigateToDreamData

    fun onDreamClicked(id: Int) {
        _navigateToDreamData.value = id
    }

    fun onDreamNavigated() {
        _navigateToDreamData.value = null
    }

    fun map(letter: String): Int {
        val map = mutableMapOf(
            "А" to 0,
            "Б" to 140,
            "В" to 436,
            "Г" to 612,
            "Д" to 809,
            "Е" to 985,
            "Ж" to 1073,
            "З" to 1129,
            "И" to 1305,
            "Й" to 1434,
            "К" to 1438,
            "Л" to 1861,
            "М" to 1994,
            "Н" to 2224,
            "О" to 2383,
            "П" to 2558,
            "Р" to 3209,
            "С" to 3406,
            "Т" to 3799,
            "У" to 3941,
            "Ф" to 4004,
            "Х" to 4091,
            "Ц" to 4184,
            "Ч" to 4234,
            "Ш" to 4315,
            "Щ" to 4384,
            "Ъ" to 4399,
            "Ю" to 4401,
            "Я" to 4415
        )
        return map[letter]!!
    }
}

