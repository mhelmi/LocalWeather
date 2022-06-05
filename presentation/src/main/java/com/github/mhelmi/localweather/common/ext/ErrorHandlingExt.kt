package com.github.mhelmi.localweather.common.ext

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.github.mhelmi.localweather.R
import com.github.mhelmi.localweather.common.state.UiState
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Throwable.toUiState() = UiState.Error<T>(toAppError().message)

fun Throwable.toAppError(): AppError {
  Timber.e(this)
  return when (this) {
    is HttpException -> {
      val response = this.response()
      val error = response?.errorBody()
      val httpCode = response?.code() ?: UNKNOWN_ERROR
      when {
        error == null || error.contentLength() == 0L -> getDefaultError()
        else -> try {
          val json = error.string().also { Timber.e(it) }
          Gson().fromJson(json, WeatherErrorRemote::class.java).error ?: getDefaultError()
        } catch (e: JsonSyntaxException) {
          Timber.e(e, "convert error Exception")
          getDefaultError()
        }
      }
    }
    is MalformedJsonException,
    is JsonParseException,
    is EOFException -> getDefaultError()
    is SocketTimeoutException,
    is UnknownHostException,
    is ConnectException,
    is IOException -> getDefaultError(messageRes = R.string.error_no_internet)
    else -> getDefaultError()
  }
}

fun getDefaultError(messageRes: Int = R.string.error_default) = AppError(
  code = UNKNOWN_ERROR,
  message = null,
  messageRes = messageRes
)

const val UNKNOWN_ERROR = 10000


data class WeatherErrorRemote(
  @SerializedName("error")
  val error: AppError?
)

@Keep
data class AppError(
  @SerializedName("code")
  val code: Int?,
  @SerializedName("message")
  val message: String?,
  @StringRes val messageRes: Int? = null
)