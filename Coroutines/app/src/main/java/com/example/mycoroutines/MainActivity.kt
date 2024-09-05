package com.example.mycoroutines

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

class CoRoutineImageExample : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val imageUrl = "https://img.freepik.com/free-vector/cute-happy-smiling-child-isolated-white_1308-32243.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)

// Launch a coroutine in the lifecycleScope
        lifecycleScope.launch(Dispatchers.IO) {
            val imageData = fetchImage(imageUrl)
            if (imageData != null) {
// Decode the byte array into a bitmap
                val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

// Switch to the main thread to update UI
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                }
            }

        }
    }

    // Suspending function to fetch image data
    private suspend fun fetchImage(url: String): ByteArray? {
        return withContext(Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                response.body?.bytes()
            } else {
                null
            }
        }
    }
}