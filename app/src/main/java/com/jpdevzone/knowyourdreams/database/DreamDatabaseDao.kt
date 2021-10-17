package com.jpdevzone.knowyourdreams.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface DreamDatabaseDao {

    @Update
    fun update(dream: Dream)

    @Query("UPDATE dream_list_table set isChecked = :status, modifiedAt = :modifiedAt WHERE id = :key")
    fun updateChecked(key: Int, status: Boolean, modifiedAt: Long)

    @Query("UPDATE dream_list_table SET isChecked = :status, modifiedAt = :modifiedAt WHERE id = :key" )
    fun updateFavouritesById(key: Int, status: Boolean, modifiedAt: Long)

    @Query("UPDATE dream_list_table SET isChecked = :status")
    fun updateAllFavouritesToFalse(status: Boolean)

    @Query("UPDATE dream_list_table SET inHistory = :status, visitedAt = :visitedAt WHERE id = :key")
    fun updateHistoryById(key: Int, status: Boolean, visitedAt: Long)

    @Query("UPDATE dream_list_table SET inHistory = :status")
    fun updateAllHistoryToFalse(status: Boolean)

    @Query("SELECT * from dream_list_table WHERE id = :key" )
    fun get(key: Int): LiveData<Dream>

    @Query("SELECT * from dream_list_table ORDER BY id ASC")
    fun getAllDrams(): LiveData<List<Dream>>

    @Query("SELECT * from dream_list_table WHERE isChecked = :checked ORDER BY modifiedAt DESC")
    fun getFavourites(checked: Boolean): LiveData<List<Dream>>

    @Query("SELECT * from dream_list_table WHERE inHistory = :checked ORDER BY visitedAt DESC")
    fun getHistory(checked: Boolean): LiveData<List<Dream>>

    @Query("UPDATE dream_list_table set visitedAt = :visitedAt WHERE id = :key")
    fun updateVisitedAt(key: Int, visitedAt: Long)

    @Query("SELECT * from dream_list_table WHERE dreamItem LIKE :searchQuery")
    fun searchDreams(searchQuery: String): LiveData<List<Dream>>
}