package com.example.musicplayer.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.AlbumViewAdapter
import com.example.musicplayer.MainActivity
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAlbumsBinding

class AlbumsFragment : Fragment() {
    private var parentActivityAlbum: MainActivity? = null
    private var _binding: FragmentAlbumsBinding? = null
    private lateinit var recyclerViewAlbum: RecyclerView
    lateinit var albumViewAdapter: AlbumViewAdapter


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View
    {
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is MainActivity) parentActivityAlbum = activity as MainActivity
        recyclerViewAlbum = view.findViewById(R.id.albums_recycler_view)
//        val tempList = ArrayList<String>()
//        tempList.add("Song Album 1")
//        tempList.add("Song Album 2")
//        tempList.add("Song Album 3")
//        tempList.add("Song Album 4")
        parentActivityAlbum!!.AlbumListFA = parentActivityAlbum!!.getAllAudio()
        recyclerViewAlbum.setHasFixedSize(true)
        recyclerViewAlbum.setItemViewCacheSize(10)
        recyclerViewAlbum.layoutManager = GridLayoutManager(requireContext(),2)
        albumViewAdapter = AlbumViewAdapter(requireContext(),parentActivityAlbum!!.AlbumListFA)
        recyclerViewAlbum.adapter = albumViewAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
