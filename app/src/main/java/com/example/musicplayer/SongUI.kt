package com.example.musicplayer

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class SongUI : Fragment(), MediaPlayer.OnCompletionListener {
    private var parentMainActivity: MainActivity? = null
    private var musicService:MusicService? = null
    //init
    lateinit var previous_btn: ImageView
    lateinit var play_pause_btn: ImageView
    lateinit var next_btn: ImageView
    lateinit var songName: TextView
    lateinit var artistName: TextView
    lateinit var start_duration: TextView
    lateinit var end_duration: TextView
    lateinit var song_poster: ImageView
    lateinit var seek_bar: SeekBar
    lateinit var runnable: Runnable

     val musicListPA: ArrayList<TracksManager?> = ArrayList()
    private var songPosition: Int = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = FragmentSongUiBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_ui, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val intent = Intent(context, MusicService::class.java)
//        requireActivity().bindService(intent, this, Context.BIND_AUTO_CREATE)
//        requireActivity().startService(intent)

        if (activity is MainActivity) parentMainActivity = activity as MainActivity
        //var mFA1 = (activity as MainActivity).MusicListFA
        seek_bar = view.findViewById(R.id.seekbar_current_song)
        play_pause_btn = view.findViewById(R.id.play_pause_track)
        songName = view.findViewById(R.id.current_song_name)
        artistName = view.findViewById(R.id.currentSong_artist_name)
        song_poster = view.findViewById(R.id.current_song_poster)
        start_duration = view.findViewById(R.id.start_duration)
        end_duration = view.findViewById(R.id.end_duration)
        previous_btn = view.findViewById(R.id.previous_track)
        next_btn = view.findViewById(R.id.next_track)

        //previous, next tracks
        previous_btn.setOnClickListener {
            changeSong(false)
        }
        next_btn.setOnClickListener {
            changeSong(true)
        }
//        mediaPlayer?.setOnCompletionListener(MediaPlayer.OnCompletionListener { it
//            changeSong(true)
//        })
        //seekbar
        seek_bar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayer!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        songPosition = requireArguments().getInt("index", 0)
        when (requireArguments().getString("class")) {
            "TrackListAdapter" -> {
                musicListPA.addAll(parentMainActivity!!.MusicListFA)
                songInit()
                initializeMediaPlayer()
                setSeekBar()
                play_pause_btn.setOnClickListener {
                    if (isPlaying) pauseMusic()
                    else playMusic()
                }
            }

            "AlbumListAdapter" -> {
                parentMainActivity?.AlbumListFA?.let { musicListPA.addAll(it) }
                songInit()
                requireActivity().startService(Intent(requireActivity(),MusicService::class.java))
//                initializeMediaPlayer()
               // seekBarSyncDuration()  //remove if initializeMediaPlayer() is there
//                setSeekBar()
//                play_pause_btn.setOnClickListener {
//                    if (isPlaying) pauseMusic()
//                    else playMusic()
//                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
//        requireActivity().stopService(Intent(requireContext(),MusicService::class.java))
  mediaPlayer!!.stop()  //1
    }

    private fun playMusic() {
        play_pause_btn.setImageResource(R.drawable.pause_vector)
        isPlaying = true
       mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        play_pause_btn.setImageResource(R.drawable.play_vector)
        isPlaying = false
        mediaPlayer!!.pause()
    }

    private fun setSeekBar() {
        runnable = Runnable {
            start_duration.text = formatDuration(mediaPlayer!!.currentPosition.toLong())
            seek_bar.progress = mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }

    private fun initializeMediaPlayer() {
        if (mediaPlayer == null) mediaPlayer = MediaPlayer()
        mediaPlayer!!.reset()
        mediaPlayer!!.setDataSource(musicListPA[songPosition]?.path)
        mediaPlayer!!.prepare()
        mediaPlayer!!.start()
        isPlaying = true
        seekBarSyncDuration()
    }

    private fun changeSong(change: Boolean) {
        if (change) {
            if (musicListPA.size - 1 == songPosition)
                songPosition = 0
            else {
                ++songPosition
                songInit()
                initializeMediaPlayer()
            }
        } else {
            if (0 == songPosition)
                songPosition = musicListPA.size - 1
            else {
                --songPosition
                songInit()
                initializeMediaPlayer()
            }
        }
    }

    private fun songInit() {

        Glide.with(this)
            .load(musicListPA[songPosition]?.artUri)
            .apply(RequestOptions().placeholder(R.drawable.track_img).centerCrop())
            .into(song_poster)
        songName.text = musicListPA[songPosition]?.title
        artistName.text = musicListPA[songPosition]?.artist
       play_pause_btn.setImageResource(R.drawable.pause_vector)
    }

   private fun seekBarSyncDuration() {
        start_duration.text = formatDuration(mediaPlayer!!.currentPosition.toLong())
        end_duration.text = formatDuration(mediaPlayer!!.duration.toLong())
        seek_bar.progress = 0
        seek_bar.max = mediaPlayer!!.duration
    }

//    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
////        val binder = service as MusicService.MyBinder
////        musicService = binder.currentService()
//    }
//
//    override fun onServiceDisconnected(name: ComponentName?) {
//       // musicService = null
//    }

    override fun onCompletion(mp: MediaPlayer) {
        changeSong(true)


    }
}