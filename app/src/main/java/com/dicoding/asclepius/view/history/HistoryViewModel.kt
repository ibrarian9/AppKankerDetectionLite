package com.dicoding.asclepius.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.remote.AsclepiusRepository
import com.dicoding.asclepius.remote.database.History

class HistoryViewModel(private val repository: AsclepiusRepository): ViewModel() {

    fun getAllHistory(): LiveData<List<History>> = repository.getAllHistory()
}