package com.example.echo.databases

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.echo.Songs
import com.example.echo.fragments.SongPlayingFragment

class EchoDatabase : SQLiteOpenHelper {

    var _songList = ArrayList<Songs>()

    object Statified {
        var DB_VERSION = 13
        val DB_NAME = "FavouriteDatabase"
        val TABLE_NAME = "FavouriteTable"
        val COLUMN_ID = "SongId"
        val COLUMN_SONG_TITLE = "SongTitle"
        val COLUMN_SONG_ARTIST = "SongArtist"
        val COLUMN_SONG_PATH = "SongPath"
    }

    override fun onCreate(sqlLiteDatabase: SQLiteDatabase) {
        sqlLiteDatabase.execSQL(" CREATE TABLE " + Statified.TABLE_NAME + " ( " + Statified.COLUMN_ID + " INTEGER PRIMARY KEY, " + Statified.COLUMN_SONG_ARTIST + " TEXT, " +
                Statified.COLUMN_SONG_TITLE + " TEXT, " + Statified.COLUMN_SONG_PATH + " TEXT); " ) }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int,newVersion: Int) {

    }

    constructor(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : super(
        context,
        name,
        factory,
        version
    )

    constructor(context: Context?) : super(
        context,
        Statified.DB_NAME,
        null,
        Statified.DB_VERSION
    )

    fun storeAsFavourite(id: Int?, artist: String?, songTitle: String?, path: String?) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Statified.COLUMN_ID, id)
        contentValues.put(Statified.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Statified.COLUMN_SONG_TITLE, songTitle)
        contentValues.put(Statified.COLUMN_SONG_PATH, path)
        db.insert(Statified.TABLE_NAME, null, contentValues)
        db.close()
    }

    fun queryDBList(): ArrayList<Songs>? {
        try {

            val db = this.readableDatabase
            val query_params = " SELECT * FROM " + Statified.TABLE_NAME
            val cSor = db.rawQuery(query_params, null)
            if (cSor.moveToFirst()) {
                do {

                    var _id = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_ID))
                    var _artist = cSor.getString(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_ARTIST))
                    var _title = cSor.getString(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_TITLE))
                    var _songPath = cSor.getString(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_PATH))
                    _songList.add(Songs(_id.toLong(), _title, _artist, _songPath, 0))
                } while (cSor.moveToNext())
            } else {
                return null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _songList
    }

    fun checkifIdExist(_id: Int): Boolean
    {
        var storeId = -1090
        val db = this.readableDatabase
        val query_params: String = " SELECT * FROM " + Statified.TABLE_NAME + " WHERE SongId = '$_id'"
        val cSor = db.rawQuery(query_params,null)
        if (cSor.moveToFirst()) {
            do {
                storeId = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_ID))
            } while (cSor.moveToNext())
        } else {
           return false
        }
        return storeId != -1090
    }

    fun deleteFavourite(_id: Int) {
        val db = this.writableDatabase
        db.delete(Statified.TABLE_NAME, Statified.COLUMN_ID + " = " + _id, null)
        db.close()
    }

    fun checkSize() : Int {
        var counter = 0
        val db = this.readableDatabase
        val query_params = " SELECT * FROM " + Statified.TABLE_NAME
        val cSor = db.rawQuery(query_params,null)
        if(cSor.moveToFirst()){
            do{
                counter = counter + 1
            }while (cSor.moveToNext())
        }else{
            return 0
        }
        return counter
    }
}

