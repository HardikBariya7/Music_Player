package com.example.musicplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log


class MusicService : Service() {
    private var musicListPA: ArrayList<TracksManager> = ArrayList()
    private var mediaPlayer:MediaPlayer? = MediaPlayer()
    private val songPosition:Int = 0


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("show","startCommand")
        mediaPlayer = MediaPlayer()
        Log.d("show","mediaplayerCreated")
        mediaPlayer?.reset()
        Log.d("show","mediaplayerReset")

        try { mediaPlayer?.setDataSource(musicListPA[songPosition].path)
            Log.d("show","setDataSource")
            mediaPlayer?.prepare()
            mediaPlayer?.start()
        }
        catch (e:Exception) {
            e.printStackTrace()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    /*
    //
    //    override fun onBind(intent: Intent?): IBinder {
    //        return myBinder
    //    }

    //    inner class MyBinder: Binder(){
    //        fun currentService(): MusicService {
    //            return this@MusicService
    //        }
    //    }
    //    fun createMediaPlayer(){
    //        try {
    //            if (mediaPlayer == null) mediaPlayer = MediaPlayer()
    //            mediaPlayer!!.reset()
    //            mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
    //            mediaPlayer!!.prepare()
    //
    //        }catch (e: Exception){return}
    //    }


    /*
        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         val intent1 = Intent()
            intent1.action = "com.example.services.MPLAYER"

                if (mediaPlayer == null) mediaPlayer = MediaPlayer()
                mediaPlayer!!.reset()
                mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()
            binding.startDuration.text = formatDuration(mediaPlayer!!.currentPosition.toLong())
            binding.endDuration.text = formatDuration(mediaPlayer!!.duration.toLong())
            binding.seekbarCurrentSong.progress = 0
            binding.seekbarCurrentSong.max = mediaPlayer!!.duration

            binding.playPauseTrack.setOnClickListener {
                if (isPlaying) pauseMusic()
                else playMusic()
            }
            binding.seekbarCurrentSong.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) mediaPlayer!!.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            })
    //
    //        action = "SHOW"
    //        intent1.putExtra("DataPassed", action)
    //        sendBroadcast(intent1)
            //isPlaying = true

            return START_NOT_STICKY
        }

        override fun onDestroy() {
            super.onDestroy() //       if (mediaPlayer.isPlaying())
            mediaPlayer!!.release()
        }
        private fun playMusic() {
            binding.playPauseTrack.setImageResource(R.drawable.pause_vector)
            isPlaying = true
            mediaPlayer!!.start()
        }

        private fun pauseMusic() {
            binding.playPauseTrack.setImageResource(R.drawable.play_vector)
            isPlaying = false
            mediaPlayer!!.pause()
        }
    */

         */

}
