package com.github.mhelmi.localweather.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.github.mhelmi.localweather.common.ext.hideKeyboard
import com.github.mhelmi.localweather.common.views.CustomProgressBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

  protected lateinit var navController: NavController

  @Inject
  lateinit var progressBar: CustomProgressBar

  var binding: VB? = null
    private set

  protected abstract fun onBind(inflater: LayoutInflater, container: ViewGroup?): VB

  private val backPressCallback = object : OnBackPressedCallback(false) {
    override fun handleOnBackPressed() = this@BaseFragment.onBackPressed()
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    activity
      ?.onBackPressedDispatcher
      ?.addCallback(this, this@BaseFragment.backPressCallback)
  }

  @CallSuper
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableBackPressedHandle()
  }

  @CallSuper
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupNavController()
  }

  override fun onResume() {
    super.onResume()
    setOnTextChangeListeners()
  }

  override fun onStop() {
    hideLoading()
    hideKeyboard()
    super.onStop()
  }

  private fun setupNavController() {
    // Try catch important For EX: to handle using BaseFragment inside viewpager without navController
    try {
      navController = findNavController()
    } catch (e: IllegalStateException) {
      e.printStackTrace()
    }
  }

  /**
   * Put any text change listeners here, especially searchViews
   * QueryTextListener to solve known issue of searchView.
   */
  open fun setOnTextChangeListeners(): ViewBinding? = null

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    binding = onBind(inflater, container)
    return binding?.root ?: return null
  }

  open fun setLoading(
    isLoading: Boolean,
    disableTouching: Boolean = false,
  ) = if (isLoading) showLoading(disableTouching) else hideLoading()

  open fun showLoading(disableTouching: Boolean = false) {
    if (::progressBar.isInitialized) progressBar.show(disableTouching)
  }

  open fun hideLoading() {
    if (::progressBar.isInitialized) progressBar.hide()
  }

  /**
   * When [isEnabled] = false, then parent container Activity will handle back press,
   * otherwise fragment will handle it
   */
  protected open fun enableBackPressedHandle(isEnabled: Boolean = true) {
    backPressCallback.isEnabled = isEnabled
  }

  open fun onBackPressed() {
    if (::navController.isInitialized) navController.navigateUp()
  }

  override fun onDestroyView() {
    binding = null
    super.onDestroyView()
  }
}
