package com.example.myciceronenavigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

/**
 * Created by terrakok 26.11.16
 */
class ForwardFragment : Fragment(), BackButtonListener {

  private var toolbar: Toolbar? = null
  private var chainTV: TextView? = null
  private var forwardBt: View? = null
  private var githubBt: View? = null

  lateinit var forwardViewModel: ForwardViewModel

  var name: String = ""

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    name = arguments?.getString(EXTRA_NAME) ?: ""
    Log.d("TAGG", "$name onCreateView")
    return inflater.inflate(R.layout.fragment_forward, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    toolbar = view.findViewById(R.id.toolbar) as Toolbar
    chainTV = view.findViewById(R.id.chain_text) as TextView
    forwardBt = view.findViewById(R.id.forward_button)
    githubBt = view.findViewById(R.id.github_button)

    Log.d("TAGG", "$name onViewCreated")

    val router = (parentFragment as RouterProvider).getRouter()
    name = arguments?.getString(EXTRA_NAME) ?: ""
    val number = arguments?.getInt(EXTRA_NUMBER) ?: 0
    forwardViewModel = ViewModelProviders.of(this, ForwardViewModelFactory(router, name, number)).get(ForwardViewModel::class.java)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)


    Log.d("TAGG", "$name onActivityCreated")

    toolbar!!.title = arguments!!.getString(EXTRA_NAME)
    toolbar!!.setNavigationOnClickListener { forwardViewModel.onBackPressed() }
    forwardBt!!.setOnClickListener { forwardViewModel.onForwardPressed()}
//    githubBt!!.setOnClickListener { presenter!!.onGithubPressed() }

    forwardViewModel.mutableLiveData.observe(this, Observer {
      setChainText(it)
    })
  }

  override fun onStart() {
    super.onStart()

    Log.d("TAGG", "$name onStart")
  }

  override fun onStop() {
    super.onStop()

    Log.d("TAGG", "$name onStop")
  }

  fun setChainText(chainText: String) {
    chainTV!!.text = chainText
  }

  override fun onBackPressed(): Boolean {
    forwardViewModel.onBackPressed()
    return true
  }

  companion object {
    private val EXTRA_NAME = "extra_name"
    private val EXTRA_NUMBER = "extra_number"

    fun getNewInstance(name: String, number: Int): ForwardFragment {
      val fragment = ForwardFragment()

      val arguments = Bundle()
      arguments.putString(EXTRA_NAME, name)
      arguments.putInt(EXTRA_NUMBER, number)
      fragment.arguments = arguments

      return fragment
    }
  }
}
