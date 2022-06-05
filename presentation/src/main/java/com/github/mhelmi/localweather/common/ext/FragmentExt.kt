package com.github.mhelmi.localweather.common.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.hideKeyboard() {
  val inputMethodManager =
    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
  inputMethodManager?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun Fragment.collectFlow(
  state: Lifecycle.State = Lifecycle.State.STARTED,
  block: suspend CoroutineScope.() -> Unit
) {
  viewLifecycleOwner.lifecycleScope.launch {
    viewLifecycleOwner.repeatOnLifecycle(state) {
      block()
    }
  }
}