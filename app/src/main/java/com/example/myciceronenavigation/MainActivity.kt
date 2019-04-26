package com.example.myciceronenavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Router

class MainActivity : AppCompatActivity(), RouterProvider {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initViews()

    if (savedInstanceState == null) {
      bottomNavView.selectedItemId = BottomType.ANDROID.ordinal
    }
  }

  override fun onBackPressed() {
    val fm = supportFragmentManager
    var fragment: Fragment? = null
    val fragments = fm.fragments
    for (f in fragments) {
      if (f.isVisible) {
        fragment = f
        break
      }
    }
    if (fragment is BackButtonListener && (fragment as BackButtonListener).onBackPressed()) return

    getRouter().exit()
  }

  override fun getRouter(): Router {
    return app.router
  }

  private fun initViews() {
    bottomNavView.menu.apply {
      add(Menu.NONE, BottomType.ANDROID.ordinal, BottomType.ANDROID.ordinal, BottomType.ANDROID.name).setIcon(R.drawable.ic_android_white_24dp)
      add(Menu.NONE, BottomType.BUG.ordinal, BottomType.BUG.ordinal, BottomType.BUG.name).setIcon(R.drawable.ic_bug_report_white_24dp)
      add(Menu.NONE, BottomType.DOG.ordinal, BottomType.DOG.ordinal, BottomType.DOG.name).setIcon(R.drawable.ic_pets_white_24dp)
    }

    bottomNavView.setOnNavigationItemSelectedListener(itemSelectedListener)
    bottomNavView.setOnNavigationItemReselectedListener {
      itemSelectedListener.onNavigationItemSelected(it)
    }
  }

  private val itemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
    when (val type = BottomType.values()[it.itemId]) {
      BottomType.ANDROID -> selectTab(type)
      BottomType.BUG -> selectTab(type)
      BottomType.DOG -> selectTab(type)
    }
    true
  }

  private fun selectTab(type: BottomType) {
    val fm = supportFragmentManager
    var currentFragment: Fragment? = null
    val fragments = fm.fragments
    for (f in fragments) {
      if (f.isVisible) {
        currentFragment = f
        break
      }
    }
    val newFragment = fm.findFragmentByTag(type.name)

    if (newFragment != null && currentFragment === newFragment) return

    val transaction = fm.beginTransaction()
    if (newFragment == null) {
      transaction.add(R.id.mainContainer, Screens.TabScreen(type.name).fragment, type.name)
    }

    if (currentFragment != null) {
      transaction.hide(currentFragment)
    }

    if (newFragment != null) {
      transaction.show(newFragment)
    }
    transaction.commitNow()
  }

  enum class BottomType {
    ANDROID, BUG, DOG
  }
}