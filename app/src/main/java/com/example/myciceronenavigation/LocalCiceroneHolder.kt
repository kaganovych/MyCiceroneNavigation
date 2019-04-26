package com.example.myciceronenavigation

import java.util.HashMap
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Created by terrakok 27.11.16
 */
class LocalCiceroneHolder {
  private val containers: HashMap<String, Cicerone<Router>> = hashMapOf()

  fun getCicerone(containerTag: String): Cicerone<Router> {
    if (!containers.containsKey(containerTag)) {
      containers[containerTag] = Cicerone.create()
    }
    return containers[containerTag]!!
  }
}
