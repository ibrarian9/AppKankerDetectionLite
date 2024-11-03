package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.ViewModelFactory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.remote.database.History

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val resultViewModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val dataImage: Uri? = intent.getParcelableExtra(IMAGE)
        val dataResult = intent.getStringExtra(RESULT)

        Glide.with(this@ResultActivity)
            .load(dataImage).into(binding.resultImage)
        binding.resultText.text = dataResult

        binding.saveHistory.setOnClickListener {
            val saveHistory = History(result = dataResult!!, imageUri = dataImage.toString())
            try {
                resultViewModel.insertHistory(saveHistory)
                Toast.makeText(this@ResultActivity, "Data berhasil disave...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception){
                Toast.makeText(this@ResultActivity, "Data gagal disave : ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("Error Input ", e.message.toString())
            }
        }
    }
    companion object {
        const val IMAGE = "image_uri"
        const val RESULT = "result"
    }
}
