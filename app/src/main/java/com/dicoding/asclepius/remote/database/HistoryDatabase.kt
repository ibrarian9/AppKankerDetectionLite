package com.dicoding.asclepius.remote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {

    abstract fun getHistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        fun getInstance(context: Context): HistoryDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java, "historyDatabase"
                ).build()
            }
    }
}