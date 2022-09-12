package com.example.myapplication.view.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.view.fragments.IndiaFragment
import com.example.myapplication.view.fragments.SportsFragment
import com.example.myapplication.view.fragments.TopStoriesFragment
import com.example.myapplication.view.fragments.WorldFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : DcbBaseActivity() {

    private val mFragmentManager = supportFragmentManager
    private lateinit var activeFragment: Fragment

    private var mTopStoriesFragment: TopStoriesFragment? = null
    private var mIndiaFragment: IndiaFragment? = null
    private var mWorldFragment: WorldFragment? = null
    private var mSportsFragment: SportsFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTopStoriesFragment = TopStoriesFragment.newInstance(0)

        initViews()
    }

    private fun initViews() {


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            bottomNavigationView.menu.findItem(item.itemId).isChecked = true
            when (item.itemId) {
                R.id.navigation_top -> {

                    mFragmentManager.beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .hide(activeFragment)
                        .show(mTopStoriesFragment!!).commit()
                    activeFragment = mTopStoriesFragment!!
                }
                R.id.navigation_india -> {

                    if (mIndiaFragment == null) {
                        mIndiaFragment = IndiaFragment.newInstance(1)
                        addFragment(mIndiaFragment)
                    } else {
                        hideShowFragment(mIndiaFragment)
                    }
                }
                R.id.navigation_world -> {

                    if (mWorldFragment == null) {
                        mWorldFragment = WorldFragment.newInstance(2)
                        addFragment(mWorldFragment)
                    } else {
                        hideShowFragment(mWorldFragment)
                    }

                }
                R.id.navigation_sports -> {

                    if (mSportsFragment == null) {
                        mSportsFragment = SportsFragment.newInstance(3)
                        addFragment(mSportsFragment)
                    } else {
                        hideShowFragment(mSportsFragment)
                    }
                }
            }
            false
        }



        mFragmentManager.beginTransaction().apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            replace(R.id.fragment_location_container, mTopStoriesFragment!!)
        }.commit()
        bottomNavigationView.menu.findItem(R.id.navigation_top).isChecked = true

        activeFragment = mTopStoriesFragment!!
    }

    private fun addFragment(frag: Fragment?) {
        mFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .hide(activeFragment)
            .add(R.id.fragment_location_container, frag!!, frag.javaClass.name)
            .commit()
        activeFragment = frag
    }

    private fun hideShowFragment(frag: Fragment?) {
        mFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .hide(activeFragment)
            .show(frag!!)
            .commit()
        activeFragment = frag
    }
}