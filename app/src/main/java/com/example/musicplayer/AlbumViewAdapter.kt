package com.example.musicplayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayer.databinding.AlbumsViewBinding


class AlbumViewAdapter(
    private val context: Context,
    private var playlistList: ArrayList<TracksManager>
) : RecyclerView.Adapter<AlbumViewAdapter.ViewHolder>() {

    class ViewHolder(binding: AlbumsViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.imgAlbumView
        val name = binding.txtSongNameAlbum
        val root = binding.root
        val artist = binding.txtArtistNameAlbum
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AlbumsViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = playlistList[position].album
        holder.artist.text = playlistList[position].artist
        holder.name.isSelected = true
        holder.root.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("index", position)
            bundle.putString("class", "AlbumListAdapter")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.action_albums_to_ui_demo_screen, bundle)
        }
        Glide.with(context)
            .load(playlistList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.track_img).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }
}