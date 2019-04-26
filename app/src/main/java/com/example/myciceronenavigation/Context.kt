package com.example.myciceronenavigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

val Context.app
  get() = (applicationContext as App)

val Fragment.app
  get() = activity!!.app