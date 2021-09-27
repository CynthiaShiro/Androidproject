package com.example.donationproject

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.IOException

class MyDBAdapter(_context: Context) {
    private val DATABASE_NAME:String = "name"
    private var mContext:Context? = null
    private val donortable = "DonorHospital"
    private var mDbHelper: MyDBHelper? = null
    private var mSqLiteDatabase: SQLiteDatabase? = null
    private val DATABASE_VERSION = 1
    init{
        this.mContext = _context
        mDbHelper = MyDBHelper(_context, DATABASE_NAME,null,DATABASE_VERSION)

    }
    fun open(){
        mSqLiteDatabase = mDbHelper?.writableDatabase
    }
    inner class MyDBHelper(context: Context?,
                     hospital:String?,factory:SQLiteDatabase.CursorFactory?,
                     version:Int): SQLiteOpenHelper(context, hospital,factory,version) {
        override fun onCreate(_db: SQLiteDatabase?) {
           try{ val query = "CREATE TABLE $donortable(id integer primary key autoincrement, hospital text,faculty integer);"
            _db?.execSQL(query)}catch (e: SQLException){
               try{
                   throw IOException(e)
               }catch (e1: IOException){
                   e1.printStackTrace()
               }
            }
        }

        override fun onUpgrade(_db: SQLiteDatabase?, _oldVersion: Int, _newVersion: Int) {
            val query = "DROP TABLE IF EXISTS $donortable;"
            _db?.execSQL(query)
            onCreate(_db)
        }
    }

    fun insertDonors(hospital: String, faculty:Int):Boolean{
        val cv = ContentValues()
        cv.put("hospital",hospital)
        cv.put("faculty",faculty)
        mSqLiteDatabase?.insert(donortable,null,cv)
        return true
    }
    fun selectalldonors():List<String>{
        var allDonors: MutableList<String> = ArrayList();
        var cursor: Cursor = mSqLiteDatabase?.query(donortable,null,null,null,null,null,null)!!
        if (cursor.moveToFirst()){
            do {
                allDonors.add(cursor.getString(1))
            }while (cursor.moveToNext())
        }
        return allDonors

    }
    fun deletealldonors(){
        mSqLiteDatabase?.delete("donors",null,null)
    }
}

