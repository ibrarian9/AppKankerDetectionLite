package com.dicoding.asclepius.remote.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistory(history: History)

    @Query("SELECT * FROM history")
    fun getAllHistory(): LiveData<List<History>>

}