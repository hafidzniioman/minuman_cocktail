package com.example.myapplication

import android.app.Application
import android.content.Intent
import com.example.myapplication.ui.activities.MainActivity
import com.example.myapplication.utils.PrefManager

class Cocktail : Application() {
    override fun onCreate() {
        super.onCreate()
        val i = when(PrefManager(applicationContext).hasLaunched()) {
            true -> Intent(this@Cocktail, MainActivity::class.java)
            else -> Intent(this@Cocktail, MainActivity::class.java)
        }
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }
}