package com.github.mhelmi.localweather.common.ext

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun SearchView.onQueryChanges(
  lifecycleCoroutineScope: LifecycleCoroutineScope,
  delayMillis: Long = 500,
  doSearchOnEmptyText: Boolean = true,
  minLength: Int = 3,
  doSearch: (query: String) -> Unit,
) {
  var queryTextChangedJob: Job? = null
  this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
      return if (query != null && query.length >= minLength) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = lifecycleCoroutineScope.launch(Dispatchers.Main) {
          doSearch(query)
        }
        this@onQueryChanges.hideKeyboard()
        this@onQueryChanges.clearFocus()
        true
      } else false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
      newText ?: return false
      return if ((newText.isEmpty() && doSearchOnEmptyText) || newText.length >= minLength) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = lifecycleCoroutineScope.launch(Dispatchers.Main) {
          delay(delayMillis)
          doSearch(newText)
        }
        true
      } else false
    }
  })
}

fun SearchView.hideKeyboard() {
  val imm: InputMethodManager? =
    ContextCompat.getSystemService(this.context, InputMethodManager::class.java)
  imm?.hideSoftInputFromWindow(this.windowToken, 0)
}