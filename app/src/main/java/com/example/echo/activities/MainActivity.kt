package com.example.echo.activities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.echo.R
import com.example.echo.adapters.NavigationDrawerAdapter
import com.example.echo.fragments.MainScreenFragment
import com.example.echo.fragments.SongPlayingFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(){

    private var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    private var imagesForNavdrawer = intArrayOf(R.drawable.navigation_allsongs,R.drawable.navigation_favorites,R.drawable.navigation_settings,R.drawable.navigation_aboutus)
    private var trackNotificationBuilder: Notification? = null

    object Statified{
        var drawerLayout: DrawerLayout? = null

        var notificationManager: NotificationManager? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Statified.drawerLayout = findViewById(R.id.drawer_Layout)

        navigationDrawerIconsList.add("All Songs")
        navigationDrawerIconsList.add("Favourites")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("About Us")
        val toggle = ActionBarDrawerToggle(this@MainActivity, Statified.drawerLayout,toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        Statified.drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.details_fragment,mainScreenFragment,"MainScreenFragment")
            .commit()

        val navigationDrawerAdapter = NavigationDrawerAdapter(navigationDrawerIconsList,imagesForNavdrawer,this)
        navigationDrawerAdapter.notifyDataSetChanged()

        val navigationRecyclerView = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigationRecyclerView.layoutManager = LinearLayoutManager(this)
        navigationRecyclerView.itemAnimator = DefaultItemAnimator()
        navigationRecyclerView.adapter = navigationDrawerAdapter
        navigationRecyclerView.setHasFixedSize(true)

        val intent = Intent(this@MainActivity, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this@MainActivity, System.currentTimeMillis().toInt(),
            intent, 0)
        trackNotificationBuilder = Notification.Builder(this)
            .setContentTitle("A track is playing in the Background")
            .setSmallIcon(R.drawable.echo_logo)
            .setContentIntent(pIntent)
            .setOngoing(true)
            .setAutoCancel(true)
            .build()
        Statified.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    override fun onStart(){
        super.onStart()
        try{
            Statified.notificationManager?.cancel(1978)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onStop(){
        super.onStop()
        try{
            if(SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1978, trackNotificationBuilder)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try{
            Statified.notificationManager?.cancel(1978)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}
