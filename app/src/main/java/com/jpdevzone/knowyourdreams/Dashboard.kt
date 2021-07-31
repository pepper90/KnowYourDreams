package com.jpdevzone.knowyourdreams

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
}