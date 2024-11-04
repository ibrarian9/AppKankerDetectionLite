package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.history.HistoryActivity
import com.dicoding.asclepius.view.news.NewsActivity
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.io.IOException
import java.text.NumberFormat
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifier: ImageClassifierHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newsButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, NewsActivity::class.java))
        }

        binding.historyButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }

        startGallery()
    }

    private fun startGallery() {
        binding.galleryButton.setOnClickListener {
            val i = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            resulLauncher.launch(i)
        }
    }

    private val resulLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        it?.let { uri ->
            showImage(uri)
            val imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imageDir, "${UUID.randomUUID()}.jpg")

            try {
                imageFile.createNewFile()
            } catch (e: IOException){
                e.printStackTrace()
                println(e.printStackTrace())
            }

            val destinationUri = Uri.fromFile(imageFile)

            val options = UCrop.Options()
            UCrop.of(uri, destinationUri)
                .withOptions(options)
                .withMaxResultSize(2000, 2000)
                .start(this@MainActivity)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            showImage(resultUri)

        } else if (requestCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            println("Error : $cropError")
        }
    }

    private fun showImage(uri: Uri?) {
        Glide.with(this@MainActivity)
            .load(uri).into(binding.previewImageView)
        binding.analyzeButton.setOnClickListener {
            if (uri != null){
                analyzeImage(uri)
            } else {
                showToast("Gambar Belum di Input...")
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        imageClassifier = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onErr(err: String) {
                    runOnUiThread {
                        showToast(err)
                    }
                }

                override fun onResult(result: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        result?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val topCategory =
                                    it[0].categories.maxByOrNull { category: Category? -> category?.score ?: 0.0f }
                                val displayResult =
                                    topCategory?.let {
                                        "${it.label} " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                                moveToResult(displayResult!!, inferenceTime, uri)
                            } else {
                                moveToResult("", inferenceTime = 0, uri)
                            }
                        }
                    }
                }
            }
        )

        imageClassifier.classifyStaticImage(uri)

    }

    private fun moveToResult(displayResult: String, inferenceTime: Long, uri: Uri) {
        val i = Intent(this, ResultActivity::class.java)
        i.putExtra(ResultActivity.RESULT, displayResult)
        i.putExtra("inference", inferenceTime)
        i.putExtra(ResultActivity.IMAGE, uri)
        startActivity(i)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
