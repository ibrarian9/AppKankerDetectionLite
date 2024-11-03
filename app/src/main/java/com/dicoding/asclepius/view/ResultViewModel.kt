package com.dicoding.asclepius.view

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.remote.AsclepiusRepository
import com.dicoding.asclepius.remote.database.History

class ResultViewModel(private val repository: AsclepiusRepository) : ViewModel() {

    fun insertHistory(history: History) = repository.insertHistory(history)
}