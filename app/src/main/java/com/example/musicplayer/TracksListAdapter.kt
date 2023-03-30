package com.example.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.TracksViewBinding

class TracksListAdapter(private val context: Context,  private var musicList: ArrayList<TracksManager>) :
    RecyclerView.Adapter<TracksListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(TracksViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.artist.text = musicList[position].artist
        holder.duration.text = formatDuration(musicList[position].duration)
        holder.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("index",position)
            bundle.putString("class","TrackListAdapter")
            val navController = findNavController(it)

            navController.navigate(R.id.action_tracks_to_ui_demo_screen,bundle)
        }
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.track_img).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    fun updateMusicList(updatedList : ArrayList<TracksManager>){
        musicList = ArrayList()
        musicList.addAll(updatedList)
        notifyDataSetChanged()
    }

    class ViewHolder(binding: TracksViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songNameTrackView
        val artist = binding.artistNameTrackView
        val image = binding.coverImg
        val duration = binding.trackDuration
        val root = binding.root

    }
}
