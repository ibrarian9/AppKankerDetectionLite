package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val dataImage: Uri? = intent.getParcelableExtra(IMAGE)
        val dataResult = intent.getStringExtra(RESULT)

        Picasso.get().load(dataImage).into(binding.resultImage)
        binding.resultText.text = dataResult
    }
    companion object {
        const val IMAGE = "image_uri"
        const val RESULT = "result"
    }
}
