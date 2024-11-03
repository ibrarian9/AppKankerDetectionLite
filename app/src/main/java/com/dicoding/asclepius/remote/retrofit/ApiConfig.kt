package com.dicoding.asclepius.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

interface ApiService {

    @GET("v2/top-headlines")
    fun getNews(
        @Query("q") query: String,
        @Query("language") language: String = "en",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = TOKEN
    ): Call<News>

    companion object {
        const val TOKEN = "f83af71b78d845239e438c581c55a17a"
    }

}