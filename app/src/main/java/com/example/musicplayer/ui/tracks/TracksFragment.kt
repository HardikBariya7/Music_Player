package com.example.musicplayer.ui.tracks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.musicplayer.*
import com.example.musicplayer.databinding.FragmentTracksBinding


class TracksFragment : Fragment() {

    private var parentActivity: MainActivity? = null
    private var binding: FragmentTracksBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var sorting_button: ImageView
//   private lateinit var tracksListAdapter: TracksListAdapter
//    var sortOrder: Int = 0
//    private val sortingList = arrayOf(MediaStore.Audio.Media.TITLE ,MediaStore.Audio.Media.DATE_ADDED,
//        MediaStore.Audio.Media.DURATION ,MediaStore.Audio.Media.SIZE )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_tracks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init activity
        if (activity is MainActivity) parentActivity = activity as MainActivity
        recyclerView = view.findViewById(R.id.tracks_recycler_view)
        swipeToRefresh = view.findViewById(R.id.swipe_to_refresh)
        sorting_button = view.findViewById(R.id.sort_btn)
        //val MusicListFA: ArrayList<TracksManager>
//        val musicList = ArrayList<String>()
//        musicList.add("1 song")

        parentActivity?.MusicListFA = parentActivity!!.getAllAudio()
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(10)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        parentActivity!!.tracksListAdapter =
            TracksListAdapter(requireContext(), parentActivity?.MusicListFA ?: arrayListOf())
        recyclerView.adapter = parentActivity!!.tracksListAdapter

        //refresh music
        swipeToRefresh.setOnRefreshListener {
            parentActivity?.MusicListFA = parentActivity!!.getAllAudio()
            parentActivity!!.tracksListAdapter.updateMusicList(parentActivity?.MusicListFA ?: arrayListOf())
            swipeToRefresh.isRefreshing = false
        }
        // sort by
        sorting_button.setOnClickListener {
            parentActivity!!.sortBuilder()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}