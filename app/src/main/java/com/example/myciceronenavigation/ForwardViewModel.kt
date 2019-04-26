package com.example.myciceronenavigation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.terrakok.cicerone.Router

class ForwardViewModel(val router: Router, val name: String, val number: Int) : ViewModel() {

  val mutableLiveData = MutableLiveData<String>()

  init {
    mutableLiveData.value = createChain(number)
  }

  private fun createChain(number: Int): String {
    var chain = "[0]"

    for (i in 0 until number) {
      chain += "➔" + (i + 1)
    }

    return chain
  }

  fun onForwardPressed() {
    router.navigateTo(Screens.ForwardScreen(name, number + 1))
  }

  fun onBackPressed() {
    router.exit()
  }

}