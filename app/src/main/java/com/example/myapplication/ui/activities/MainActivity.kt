package com.example.myapplication.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.callbacks.Callbacks
import com.example.myapplication.ui.fragment.FragmentFavorite
import com.example.myapplication.ui.fragment.FragmentHome
import com.example.myapplication.ui.fragment.FragmentSearch
import com.example.myapplication.utils.Commons
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Callbacks {
    private lateinit var linearLayout: LinearLayout
    private var currentFragment: String? = null

    private var homeFragment: Fragment? = null
    private var searchFragment: Fragment? = null
    private var favoritesFragment: Fragment? = null
    private var doubleBackToExitPressedOnce = false
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayout = findViewById(R.id.message)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        homeFragment = FragmentHome()
        searchFragment = FragmentSearch()
        favoritesFragment = FragmentFavorite()

        changeFragment(homeFragment!!, Commons.COCKTAILS)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (currentFragment == Commons.HOME) {
                    return@OnNavigationItemSelectedListener true
                }
                changeFragment(homeFragment!!, Commons.COCKTAILS)
                currentFragment = Commons.HOME
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                if (currentFragment == Commons.SEARCH) {
                    return@OnNavigationItemSelectedListener true
                }
                changeFragment(searchFragment!!, Commons.SEARCH)
                currentFragment = Commons.SEARCH
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                if (currentFragment == Commons.FAVORITES) {
                    return@OnNavigationItemSelectedListener true
                }
                changeFragment(favoritesFragment!!, Commons.FAVORITES)
                currentFragment = Commons.FAVORITES
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                if (currentFragment == Commons.INFO) {
                    return@OnNavigationItemSelectedListener true
                }
                currentFragment = Commons.INFO
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                changeFragment(homeFragment!!, Commons.COCKTAILS)
                currentFragment = Commons.HOME
                return@OnNavigationItemSelectedListener true
            }
        }
    }

    private fun changeFragment(fragment: Fragment, name: String?) {
        val manager = supportFragmentManager
        manager.popBackStack()
        manager.beginTransaction()
            .addToBackStack(fragment.tag)
            .add(R.id.message, fragment)
            .commit()

        toolbar!!.title = name
    }

    override fun onTitleFound(name: String?) {
        toolbar!!.title = name
    }

    override fun onRemoveClicked(name: String?) {}

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
