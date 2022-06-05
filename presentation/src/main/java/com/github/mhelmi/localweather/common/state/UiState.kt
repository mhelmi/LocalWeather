package com.github.mhelmi.localweather.common.state

import androidx.annotation.Keep
import androidx.annotation.StringRes

@Keep
sealed class UiState<T> {

  class Init<T> : UiState<T>()

  class Loading<T> : UiState<T>()

  data class Success<T>(val data: T) : UiState<T>()

  data class Error<T>(val message: String?) : UiState<T>()

  class Empty<T>(@StringRes val msgResId: Int = 0) : UiState<T>()
}