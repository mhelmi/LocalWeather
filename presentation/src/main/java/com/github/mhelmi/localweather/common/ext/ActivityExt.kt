package com.github.mhelmi.localweather.common.ext

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.disableTouching() {
  window?.setFlags(
    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
  )
}

fun AppCompatActivity.enableTouching() {
  window?.clearFlags(
    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
  )
}