package com.dicoding.asclepius

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.remote.AsclepiusRepository
import com.dicoding.asclepius.remote.Injection
import com.dicoding.asclepius.view.ResultViewModel
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.news.NewsViewModel

class ViewModelFactory private constructor(
    private val repo: AsclepiusRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repo) as T
            }
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(repo) as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(repo) as T
            }

            else -> throw IllegalArgumentException("uknown ViewModel Class")
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepo(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}