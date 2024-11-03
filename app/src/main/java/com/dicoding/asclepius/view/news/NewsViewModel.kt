package com.dicoding.asclepius.view.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.remote.AsclepiusRepository
import com.dicoding.asclepius.remote.retrofit.ArticlesItem

class NewsViewModel(private val repository: AsclepiusRepository): ViewModel() {

    fun getAllNews(query: String): LiveData<List<ArticlesItem>> {
        return repository.getAllNews(query)
    }
}