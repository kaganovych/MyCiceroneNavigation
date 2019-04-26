package com.example.myciceronenavigation

import android.app.Application
import ru.terrakok.cicerone.Cicerone

class App : Application() {

  val localCiceroneHolder = LocalCiceroneHolder()

  val cicerone = Cicerone.create()
  val router = cicerone.router
}