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
import es.dmoral.toasty.Toasty


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

        bottomNavigationView = binding.bottomNavigation
        val navHostFragment = fragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        fragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, historyFragment, getString(R.string.myHistory)).setMaxLifecycle(historyFragment,Lifecycle.State.CREATED).hide(historyFragment)
            add(R.id.fragmentContainer, favouritesFragment, getString(R.string.myFavourites)).setMaxLifecycle(historyFragment,Lifecycle.State.CREATED).hide(favouritesFragment)
            add(R.id.fragmentContainer, searchFragment, getString(R.string.mySearch)).setMaxLifecycle(historyFragment,Lifecycle.State.RESUMED)
        }.commit()

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

    private fun saveData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json1 = gson.toJson(Constants.history)
        val json2 = gson.toJson(Constants.favourites)
        editor.putString(Constants.SP_HISTORY, json1)
        editor.putString(Constants.SP_FAVOURITES, json2)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES,
            Context.MODE_PRIVATE)
        val gson = Gson()
        val json1 = sharedPreferences.getString(Constants.SP_HISTORY, null)
        val json2 = sharedPreferences.getString(Constants.SP_FAVOURITES, null)
        val type = object : TypeToken<ArrayList<Dream>>() {}.type
        history = gson.fromJson(json1, type)
        favourites = gson.fromJson(json2, type)

        if (history == null) {
            Constants.history = Constants.historyEmpty
        }else {
            Constants.history.clear()
            Constants.history.addAll(history!!)
        }

        if (favourites == null) {
            Constants.favourites = Constants.favouritesEmpty
        }else {
            Constants.favourites.clear()
            Constants.favourites.addAll(favourites!!)
        }
    }

    override fun onPause() {
        super.onPause()
        saveData()
    }

    override fun onBackPressed() {
        counter++
        if (counter==1) {
            saveData()
            Toasty.custom(this, R.string.toast,R.drawable.ic_exit,R.color.blue_700,Toast.LENGTH_LONG,true, true).show()
        }else {
            finish()
        }
    }
}