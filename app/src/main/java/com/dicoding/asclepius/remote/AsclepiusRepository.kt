package com.dicoding.asclepius.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.asclepius.remote.database.History
import com.dicoding.asclepius.remote.database.HistoryDao
import com.dicoding.asclepius.remote.retrofit.ApiService
import com.dicoding.asclepius.remote.retrofit.ArticlesItem
import com.dicoding.asclepius.remote.retrofit.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AsclepiusRepository private constructor (
    private val historyDao: HistoryDao,
    private val apiService: ApiService
){
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun insertHistory(history: History) = executorService.execute {
        historyDao.insertHistory(history)
    }

    fun getAllHistory(): LiveData<List<History>> = historyDao.getAllHistory()

    fun getAllNews(query: String): LiveData<List<ArticlesItem>> {

        val listNews = MutableLiveData<List<ArticlesItem>>()
        val news = apiService.getNews(query)

        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful){
                    listNews.value = response.body()!!.articles
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
                t.message
            }
        })

        return listNews
    }

    companion object {
        @Volatile
        var INSTANCE: AsclepiusRepository? = null
        fun getInstance(
            historyDao: HistoryDao,
            apiService: ApiService
        ): AsclepiusRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AsclepiusRepository(historyDao, apiService)
            }.also { INSTANCE = it }
    }
}