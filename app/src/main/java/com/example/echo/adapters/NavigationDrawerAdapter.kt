package com.example.echo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.activities.MainActivity
import com.example.echo.fragments.AboutUsFragment
import com.example.echo.fragments.MainScreenFragment
import com.example.echo.fragments.SettingsFragment

class NavigationDrawerAdapter(_contentList: ArrayList<String>, _getImages: IntArray, _context: Context)
    : RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder>() {
    private var contentList: ArrayList<String>? = null
    private var getImages: IntArray? = null
    private var mContext: Context? = null

    init {
        this.contentList = _contentList
        this.getImages = _getImages
        this.mContext = _context
    }
    override fun onBindViewHolder(holder: NavViewHolder, position: Int) {
        holder.iconGet?.setBackgroundResource(getImages?.get(position) as Int)
        holder.textGet?.text = contentList?.get(position)
        holder.contentHolder?.setOnClickListener {
            when (position) {
                0 -> {
                    val mainScreenFragment = MainScreenFragment()
                    (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment,mainScreenFragment)
                        .commit()
                }
                1 -> {
                    val settingsFragment = SettingsFragment()
                    (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment,settingsFragment)
                        .commit()
                }
                2 -> {
                    val aboutUsFragment = AboutUsFragment()
                    (mContext as MainActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment,aboutUsFragment)
                        .commit()
                }
            }
            MainActivity.Statified.drawerLayout?.closeDrawers()}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_custom_navigationdrawer,parent,false)
        return NavViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return (contentList as ArrayList).size
    }


    class NavViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var iconGet: ImageView? = null
            var textGet: TextView? = null
            var contentHolder: RelativeLayout? = null
        init {
            iconGet = itemView.findViewById(R.id.icon_navdrawer)
            textGet = itemView.findViewById(R.id.text_navdrawer)
            contentHolder = itemView.findViewById(R.id.navdrawer_item_content_holder)
        }
    }
}