package com.example.echo.databases

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.echo.Songs

class EchoDatabase(context: Context?) :
    SQLiteOpenHelper(context, Statified.DB_NAME, null, Statified.DB_VERSION) {
    private var _songsList = ArrayList<Songs>()

    object Statified {
        const val DB_NAME = "FavouriteDatabase"
        const val TABLE_NAME = "FavouriteTable"
        const val COLUMN_SONG_PATH = "SongPath"
        const val COLUMN_SONG_ARTIST = "SongArtist"
        const val COLUMN_SONG_TITLE = "SongTitle"
        const val COLUMN_ID = "SongId"
        const val DB_VERSION = 13
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + Statified.TABLE_NAME + "( " + Statified.COLUMN_ID +
                " INTEGER, " + Statified.COLUMN_SONG_ARTIST + " STRING, " + Statified.COLUMN_SONG_TITLE + " STRING, "
                + Statified.COLUMN_SONG_PATH + " STRING);")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

    }

    fun storeasFavourite(id: Int?, artist: String?, songTitle: String?, path: String?){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(Statified.COLUMN_ID, id)
        contentValues.put(Statified.COLUMN_SONG_ARTIST, artist)
        contentValues.put(Statified.COLUMN_SONG_TITLE, songTitle)
        contentValues.put(Statified.COLUMN_SONG_PATH, path)
        db.insert(Statified.TABLE_NAME, null, contentValues)
        db.close()
    }

    @SuppressLint("Recycle")
    fun queryDBforList(): ArrayList<Songs>? {
        try{
            val db = this.readableDatabase
            val queryParams = " SELECT " + " * " + " FROM " + Statified.TABLE_NAME
            val cSor = db.rawQuery(queryParams, null)
            if(cSor.moveToFirst()) {
                do{
                    val id = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_ID))
                    val artist = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_ARTIST))
                    val title = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_TITLE))
                    val songPath = cSor.getInt(cSor.getColumnIndexOrThrow(Statified.COLUMN_SONG_PATH))
                    _songsList.add(Songs(id.toLong(), title.toString(), artist.toString(), songPath.toString(), 0))
                } while(cSor.moveToNext())
            } else {
                return null
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }

        return _songsList
    }

    @SuppressLint("Recycle")
    fun checkSize(): Int {
        var counter = 0
        val db = this.readableDatabase
        val queryParams = " SELECT " + " * " + " FROM " + Statified.TABLE_NAME
        val cSor = db.rawQuery(queryParams, null)
        if (cSor.moveToFirst()) {
            do {
                counter++
            } while (cSor.moveToNext())
        } else {
            return 0
        }
        return counter
    }

    @SuppressLint("Recycle")
    fun checkIdExists(_id: Int): Boolean {
        var storeId: Int
        val db = this.readableDatabase
        val queryParams = " SELECT * FROM " + Statified.TABLE_NAME + " WHERE SongId = '$_id' "
        val cSor = db.rawQuery(queryParams, null)
        if(cSor.moveToFirst()) {
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
}