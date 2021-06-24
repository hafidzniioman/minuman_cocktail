package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.callbacks.CocktailDao
import com.example.myapplication.models.Fav
import com.example.myapplication.utils.Commons

@Database(entities = [Fav::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coctailDao(): CocktailDao
    companion object {
        private var db: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase?{
            if (db == null){
                synchronized(AppDatabase::class){
                    db = Room.databaseBuilder(context!!, AppDatabase::class.java, Commons.DRINKS)
                        .build()
                }
            }
            return db!!
        }
    }
}