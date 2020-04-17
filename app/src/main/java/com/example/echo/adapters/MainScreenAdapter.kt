package com.example.echo.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.Songs
import com.example.echo.fragments.SongPlayingFragment


class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context) : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {

    private var songDetails: ArrayList<Songs>? = null
    private var mContext: Context? = null

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(position == (songDetails as ArrayList<Songs>).size){
            
        }
            else {
            val songObject = songDetails?.get(position)
            holder.trackTitle?.text = songObject?.songTitle
            holder.trackArtist?.text = songObject?.artist
            holder.contentHolder?.setOnClickListener {
                val songPlayingFragment = SongPlayingFragment()
                val args = Bundle()
                args.putString("songArtist", songObject?.artist)
                args.putString("songTitle", songObject?.songTitle)
                args.putString("path", songObject?.songData)
                args.putInt("songId", songObject?.songID?.toInt() as Int)
                args.putInt("songPosition", position)
                args.putParcelableArrayList("songData", songDetails)
                songPlayingFragment.arguments = args
                (mContext as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment)
                    .addToBackStack("SongPlayingFragment")
                    .commit()
                if (SongPlayingFragment.Statified.currentSongHelper?.isPlaying == true) {
                    SongPlayingFragment.Statified.mediaplayer?.reset()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var itemView : View
        if(viewType == R.layout.row_custom_mainscreen_adapter)
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_custom_mainscreen_adapter,parent,false)
        else
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.plain_custom_mainscreen_adapter,parent,false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if(songDetails == null){
            0
        }else{
            (songDetails as ArrayList<Songs>).size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == (songDetails as ArrayList<Songs>).size)
            R.layout.plain_custom_mainscreen_adapter
        else
            R.layout.row_custom_mainscreen_adapter
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle)
            trackArtist = view.findViewById(R.id.trackArtist)
            contentHolder = view.findViewById(R.id.contentRowMain)
        }
    }

}