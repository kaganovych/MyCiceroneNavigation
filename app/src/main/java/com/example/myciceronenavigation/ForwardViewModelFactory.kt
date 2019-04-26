package com.example.myciceronenavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.terrakok.cicerone.Router


class ForwardViewModelFactory(
    private val router: Router,
    private val name: String,
    private val number: Int) : ViewModelProvider.Factory {


  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return ForwardViewModel(router, name, number) as T
  }
}