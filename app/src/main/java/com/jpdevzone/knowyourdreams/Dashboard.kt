package com.jpdevzone.knowyourdreams

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jpdevzone.knowyourdreams.databinding.ActivityDashboardBinding
import com.jpdevzone.knowyourdreams.favourites.FavouritesFragment
import com.jpdevzone.knowyourdreams.history.HistoryFragment
import com.jpdevzone.knowyourdreams.search.SearchFragment
import es.dmoral.toasty.Toasty

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val searchFragment = SearchFragment()
    private val historyFragment = HistoryFragment()
    private val favouritesFragment = FavouritesFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = searchFragment
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNavigationView = binding.bottomNavigation

//        bottomNavigationView.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.searchFragment->setCurrentFragment(searchFragment)
//                R.id.favouritesFragment->setCurrentFragment(favouritesFragment)
//                R.id.historyFragment->setCurrentFragment(historyFragment)
//
//            }
//            true
//        }
        val navHostFragment = fragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

//        fragmentManager.beginTransaction().apply {
//            add(R.id.fragmentContainer, historyFragment, getString(R.string.myHistory)).hide(historyFragment)
//            add(R.id.fragmentContainer, favouritesFragment, getString(R.string.myFavourites)).hide(favouritesFragment)
//            add(R.id.fragmentContainer, searchFragment, getString(R.string.mySearch))
//        }.commit()

//        initListeners()
    }
//
//    private fun setCurrentFragment(fragment:Fragment)=
//        fragmentManager.beginTransaction().apply {
//            replace(R.id.fragmentContainer,fragment)
//            commit()
//        }

//    private fun initListeners() {
//        bottomNavigationView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.searchFragment -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit()
//                    activeFragment = searchFragment
//                    true
//                }
//
//                R.id.historyFragment -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(historyFragment).commit()
//                    activeFragment = historyFragment
//                    true
//                }
//
//                R.id.favouritesFragment -> {
//                    fragmentManager.beginTransaction().hide(activeFragment).show(favouritesFragment).commit()
//                    activeFragment = favouritesFragment
//                    true
//                }
//
//                else -> false
//            }
//        }
//    }

//    override fun onBackPressed() {
//        counter++
//        if (counter==1) {
//            Toasty.custom(this, R.string.toast,R.drawable.ic_exit,R.color.blue_700,Toast.LENGTH_LONG,true, true).show()
//        } else {
//            finish()
//        }
//    }
}