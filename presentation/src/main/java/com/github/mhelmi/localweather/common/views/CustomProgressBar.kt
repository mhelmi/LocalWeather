package com.github.mhelmi.localweather.common.views

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.mhelmi.localweather.common.ext.disableTouching
import com.github.mhelmi.localweather.common.ext.enableTouching
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CustomProgressBar @Inject constructor(@ActivityContext val context: Context) {

  private val progressBar: ProgressBar

  fun show(disableTouching: Boolean = false) {
    if ((context as AppCompatActivity).isFinishing.not()) {
      progressBar.isVisible = true
      if (disableTouching) context.disableTouching()
    }
  }

  fun hide() {
    if ((context as AppCompatActivity).isFinishing.not()) {
      progressBar.isVisible = false
      context.enableTouching()
    }
  }

  init {
    val layout =
      (context as Activity).findViewById<View>(android.R.id.content).rootView as ViewGroup
    progressBar = ProgressBar(context, null, android.R.attr.progressBarStyle)
    progressBar.isIndeterminate = true
    val params = RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.MATCH_PARENT
    )
    val rl = RelativeLayout(context)
    rl.gravity = Gravity.CENTER
    rl.addView(progressBar)
    layout.addView(rl, params)
    hide()
  }
}