package com.jpdevzone.knowyourdreams

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jpdevzone.knowyourdreams.databinding.ActivityDashboardBinding
import com.jpdevzone.knowyourdreams.fragments.FavouritesFragment
import com.jpdevzone.knowyourdreams.fragments.HistoryFragment
import com.jpdevzone.knowyourdreams.fragments.SearchFragment


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var searchView: SearchView
    private lateinit var bottomNavigationView: BottomNavigationView
    private val searchFragment = SearchFragment()
    private val historyFragment = HistoryFragment()
    private val favouritesFragment = FavouritesFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = searchFragment
    private var counter = 0
    private var dreams: ArrayList<Dream>? = null
    private var history: ArrayList<Dream>? = null
    private var favourites: ArrayList<Dream>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadData()
        searchView = binding.searchView
        val input = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val searchText = searchView.findViewById<View>(input) as TextView
        val font = ResourcesCompat.getFont(this, R.font.oswald_light)
        searchText.typeface = font

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        fragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, historyFragment, getString(R.string.myHistory)).setMaxLifecycle(historyFragment,Lifecycle.State.CREATED).hide(historyFragment)
            add(R.id.fragmentContainer, favouritesFragment, getString(R.string.myFavourites)).setMaxLifecycle(historyFragment,Lifecycle.State.CREATED).hide(favouritesFragment)
            add(R.id.fragmentContainer, searchFragment, getString(R.string.mySearch)).setMaxLifecycle(historyFragment,Lifecycle.State.RESUMED)
        }.commit()

        bottomNavigationView = binding.bottomNavigation
        val navHostFragment = fragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        initListeners()
    }

    private fun initListeners() {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.searchFragment -> {
                    searchView.visibility = View.VISIBLE
                    fragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit()
                    activeFragment = searchFragment
                    true
                }

                R.id.historyFragment -> {
                    searchView.visibility = View.GONE
                    fragmentManager.beginTransaction().hide(activeFragment).show(historyFragment).commit()
                    activeFragment = historyFragment
                    true
                }

                R.id.favouritesFragment -> {
                    searchView.visibility = View.GONE
                    fragmentManager.beginTransaction().hide(activeFragment).show(favouritesFragment).commit()
                    activeFragment = favouritesFragment
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        counter++
        if (counter==1) {
            saveData()
            Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show()
        }else {
            finish()
        }
    }



    private fun saveData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(Constants.dreamList)
        val json2 = gson.toJson(Constants.history)
        val json3 = gson.toJson(Constants.favourites)
        editor.putString(Constants.SP_DREAMS, json)
        editor.putString(Constants.SP_HISTORY, json2)
        editor.putString(Constants.SP_FAVOURITES, json3)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString(Constants.SP_DREAMS, null)
        val json2 = sharedPreferences.getString(Constants.SP_HISTORY, null)
        val json3 = sharedPreferences.getString(Constants.SP_FAVOURITES, null)
        val type = object : TypeToken<ArrayList<Dream>>() {}.type
        dreams = gson.fromJson(json, type)
        history = gson.fromJson(json2, type)
        favourites = gson.fromJson(json3, type)


        if (dreams == null) {
            Constants.dreamList = Constants.getDreams()
        } else {
            Constants.dreamList.clear()
            Constants.dreamList.addAll(dreams!!)
        }

        if (history == null) {
            Constants.history = Constants.historyEmpty
        }else {
            Constants.history.addAll(history!!)
        }

        if (favourites == null) {
            Constants.favourites = Constants.favouritesEmpty
        }else {
            Constants.favourites.clear()
            Constants.favourites.addAll(favourites!!)
        }
    }

}