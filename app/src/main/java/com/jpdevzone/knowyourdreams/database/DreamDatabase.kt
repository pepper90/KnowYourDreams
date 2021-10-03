package com.jpdevzone.knowyourdreams.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Dream::class], version = 1, exportSchema = false)
abstract class DreamDatabase: RoomDatabase() {
    abstract val dreamDatabaseDao: DreamDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DreamDatabase? = null

        fun getInstance(context: Context): DreamDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DreamDatabase::class.java,
                        "dream_list_database"
                    )
                        .createFromAsset("database/dreamList.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}