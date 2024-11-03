package com.dicoding.asclepius.remote

import android.content.Context
import com.dicoding.asclepius.remote.database.HistoryDatabase
import com.dicoding.asclepius.remote.retrofit.ApiConfig

object Injection {
    fun provideRepo(context: Context): AsclepiusRepository {
        val apiService = ApiConfig.getApiService()
        val db = HistoryDatabase.getInstance(context)
        val dao = db.getHistoryDao()
        return AsclepiusRepository.getInstance(dao, apiService)
    }
}