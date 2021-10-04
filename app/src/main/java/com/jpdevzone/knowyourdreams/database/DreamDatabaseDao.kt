package com.jpdevzone.knowyourdreams.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DreamDatabaseDao {

    @Update
    fun update(dream: Dream)

    @Query("UPDATE dream_list_table SET isChecked = :status WHERE id = :key" )
    fun updateById(key: Int, status: Boolean): Dream

    @Query("SELECT * from dream_list_table WHERE id = :key" )
    fun get(key: Int): LiveData<Dream>

    @Query("SELECT * from dream_list_table WHERE isChecked = :checked")
    fun getOnlyChecked(checked: Boolean = true): LiveData<List<Dream>>

    @Query("SELECT * from dream_list_table ORDER BY id ASC")
    fun getAllDrams(): LiveData<List<Dream>>

    @Query("SELECT * from dream_list_table WHERE dreamItem LIKE :searchQuery")
    fun searchDreams(searchQuery: String): LiveData<List<Dream>>
}