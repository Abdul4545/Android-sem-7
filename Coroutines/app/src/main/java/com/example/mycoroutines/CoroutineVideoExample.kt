package com.example.mycoroutines

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CoroutineVideoExample : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var progressBar: ProgressBar
    private val videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4" // Example video URL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine_video_example)

        videoView = findViewById(R.id.videoView)
        progressBar = findViewById(R.id.progressBar)

//        lifecycleScope.launch {
//            withContext(Dispatchers.Main) {
//                progressBar.visibility = View.VISIBLE
//            }
//
//            try {
//                val videoUri = withContext(Dispatchers.IO) { fetchVideo(videoUrl) }
//                withContext(Dispatchers.Main) {
//                    progressBar.visibility = View.GONE
//                    videoView.setVideoURI(videoUri)
//                    videoView.setOnPreparedListener { mp: MediaPlayer ->
//                        mp.start()
//                    }
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    progressBar.visibility = View.GONE
//                    // Handle error, e.g., show an error message
//                }
//            }
//        }
//    }
//
//    private suspend fun fetchVideo(url: String): Uri {
//        return withContext(Dispatchers.IO) {
//            Uri.parse(url) // In this example, we just parse the URL
//            // You can add more complex logic here if needed, e.g., downloading the video
//            }


        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)


        progressBar.visibility = ProgressBar.VISIBLE

        // Set up video loading using coroutine
        CoroutineScope(Dispatchers.IO).launch {
            val videoUrl = loadVideoUrl()

            withContext(Dispatchers.Main) {
                playVideo(videoUrl)
            }
        }

        // Listen for when the video is ready to be played
        videoView.setOnPreparedListener {

            progressBar.visibility = ProgressBar.GONE
        }
    }

    private suspend fun loadVideoUrl(): String {
        delay(1000)
        return "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4" // Replace with your video URL
    }


    private fun playVideo(videoUrl: String) {
        videoView.setVideoPath(videoUrl)
        videoView.start()
    }
}