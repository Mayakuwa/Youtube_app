package com.example.youtubeplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID  = "C8Qw7vx0tT0"
const val YOUTUBE_PLAYLIST = "PLXtTjtWmQhg1SsviTmKkWO5n0a_-T0bnD"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YoutubeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // layoutInflaterで要素を追加できる
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

//        val button1 = Button(this)
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        button1.text = "Button add"
//        layout.addView(button1)

        val playView = YouTubePlayerView(this)
        playView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playView)

        // Initializing API
        playView.initialize(getString(R.string.GOOGLE_API_KEY), this);
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?,
        wasRestored: Boolean) {

        Log.d(TAG, "onIntializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onIntializationSuccess: youtubePlayer is ${youtubePlayer?.javaClass}")
        Toast.makeText(this, "Intializined Youtube Player successfully",  Toast.LENGTH_SHORT).show()

        youtubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youtubePlayer?.setPlaybackEventListener(playbackEventListener)


        if(!wasRestored) {
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            youtubePlayer?.play()
        }

    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubeIntializationResult: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0
        if(youtubeIntializationResult?.isUserRecoverableError == true) {
            youtubeIntializationResult.getErrorDialog(this, REQUEST_CODE).show()
        } else {
            val errorMessage = "There wan an error initializing the YoutubePlayer($youtubeIntializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Good, video is playing ok", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@YoutubeActivity, "video has stopped", Toast.LENGTH_SHORT).show()
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "video is paused", Toast.LENGTH_SHORT).show()
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click Ad now, make the video creator rich", Toast.LENGTH_SHORT).show()
        }

        override fun onLoading() {

        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Video has started", Toast.LENGTH_SHORT).show()
        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Congratulations ! You've completed another video", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {

        }
    }
}