package com.dicoding.asclepius.view.history

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.ViewModelFactory
import com.dicoding.asclepius.adapter.HistoryAdapter
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import com.dicoding.asclepius.remote.database.History

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyViewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = HistoryAdapter()
        binding.rvHistory.let {
            it.layoutManager = LinearLayoutManager(this@HistoryActivity)
            it.adapter = adapter
        }

        historyViewModel.getAllHistory().observe(this@HistoryActivity){ data ->
            val dataList = arrayListOf<History>()
            data.map {
                val history = History(result = it.result, imageUri = it.imageUri)
                dataList.add(history)
            }
            adapter.submitList(dataList)
        }
    }
}