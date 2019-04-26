package com.example.myciceronenavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class TabContainerFragment : Fragment(), RouterProvider, BackButtonListener {

  private var navigator: Navigator? = null

  companion object {
    private const val EXTRA_NAME = "tcf_extra_name"
    fun getNewInstance(name: String): TabContainerFragment {
      val fragment = TabContainerFragment()

      val arguments = Bundle()
      arguments.putString(EXTRA_NAME, name)
      fragment.arguments = arguments

      return fragment
    }
  }

  override fun getRouter(): Router {
    return getCicerone().router
  }

  private fun getContainerName(): String {
    return arguments?.getString(EXTRA_NAME) ?: ""
  }

  private fun getCicerone(): Cicerone<Router> {
    return app.localCiceroneHolder.getCicerone(getContainerName())
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_tab_container, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    if (childFragmentManager.findFragmentById(R.id.ftc_container) == null) {
      getCicerone().router.replaceScreen(Screens.ForwardScreen(getContainerName(), 0))
    }
  }

  override fun onResume() {
    super.onResume()
    getCicerone().navigatorHolder.setNavigator(getNavigator())
  }

  override fun onPause() {
    getCicerone().navigatorHolder.removeNavigator()
    super.onPause()
  }

  private fun getNavigator(): Navigator {
    return navigator ?: SupportAppNavigator(activity, childFragmentManager, R.id.ftc_container)
  }

  override fun onBackPressed(): Boolean {
    val fragment = childFragmentManager.findFragmentById(R.id.ftc_container)
    return if (fragment != null
        && fragment is BackButtonListener
        && (fragment as BackButtonListener).onBackPressed()) {
      true
    } else {
      (activity as RouterProvider).getRouter().exit()
      true
    }
  }
}