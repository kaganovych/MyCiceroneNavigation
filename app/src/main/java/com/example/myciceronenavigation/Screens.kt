package com.example.myciceronenavigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

  class ForwardScreen(private val containerName: String, private val number: Int) : SupportAppScreen() {

    override fun getFragment(): Fragment {
      return ForwardFragment.getNewInstance(containerName, number)
    }
  }

  class TabScreen(private val tabName: String) : SupportAppScreen() {

    override fun getFragment(): Fragment {
      return TabContainerFragment.getNewInstance(tabName)
    }
  }
}