package com.github.mhelmi.localweather.features.weather_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.mhelmi.localweather.common.state.UiState
import com.github.mhelmi.localweather.domain.model.*
import com.github.mhelmi.localweather.domain.weather.GetWeatherForecastUseCase
import com.github.mhelmi.localweather.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsInstanceOf
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherDetailsViewModelTest {

  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  private val testDispatcher = TestCoroutineDispatcher()

  private lateinit var weatherDetailsViewModel: WeatherDetailsViewModel

  @Mock
  lateinit var getWeatherForecastUseCase: GetWeatherForecastUseCase

  private val testResults: MutableList<UiState<CurrentWeatherForecast>> = mutableListOf()

  private val successResponse = CurrentWeatherForecast(
    city = City(
      "Riyadh",
      "Riyadh",
      "Saudi Arabia",
      31.56,
      31.56,
      false
    ),

    currentWeather = CurrentWeather(
      42.0,
      Condition(
        "Sunny",
        "https://csn.weather.com/sunny.png"
      ),
      120.0,
      35.0,
      "N",
      12.0,
      41.0
    ),
    listOf(
      ForecastDay(
        "05-06-2022",
        1641454541,
        DayWeather(
          38.0,
          80.0,
          8.0,
          Condition(
            "Sunny",
            "https://csn.weather.com/sunny.png"
          )
        )
      ),
      ForecastDay(
        "06-06-2022",
        1641454552,
        DayWeather(
          45.0,
          40.0,
          8.0,
          Condition(
            "Very Sunny",
            "https://csn.weather.com/very_sunny.png"
          )
        )
      )
    )
  )

  @Before
  fun beforeEachTest() {
    weatherDetailsViewModel = WeatherDetailsViewModel(getWeatherForecastUseCase, testDispatcher)
  }

  @After
  fun afterEachTest() {
    testResults.clear()
    testDispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `when loadWeatherForecast() then return Init then loading ui state`() =
    runBlockingTest {
      // Arrange
      Mockito.`when`(getWeatherForecastUseCase("Riyadh"))
        .thenReturn(flowOf(successResponse))

      val job = launch {
        weatherDetailsViewModel.uiState.toList(testResults)
      }

      // Act
      weatherDetailsViewModel.loadWeatherForecast("Riyadh")

      // Assert
      MatcherAssert.assertThat(testResults[0], IsInstanceOf.instanceOf(UiState.Init::class.java))
      MatcherAssert.assertThat(testResults[1], IsInstanceOf.instanceOf(UiState.Loading::class.java))
      job.cancel()
    }

  @Test
  fun `when loadWeatherForecast() and getWeatherForecastUseCase() return success response then return success ui state`() =
    runBlockingTest {
      Mockito.`when`(getWeatherForecastUseCase("Riyadh"))
        .thenReturn(flowOf(successResponse))

      val job = launch {
        weatherDetailsViewModel.uiState.toList(testResults)
      }
      weatherDetailsViewModel.loadWeatherForecast("Riyadh")

      MatcherAssert.assertThat(testResults[0], IsInstanceOf.instanceOf(UiState.Init::class.java))
      MatcherAssert.assertThat(testResults[1], IsInstanceOf.instanceOf(UiState.Loading::class.java))
      MatcherAssert.assertThat(testResults[2], IsInstanceOf.instanceOf(UiState.Success::class.java))
      Assert.assertEquals(successResponse, (testResults[2] as UiState.Success).data)
      job.cancel()
    }

  @Test
  fun `when loadWeatherForecast() and getWeatherForecastUseCase() return error response then return Error state`() =
    runBlockingTest {
      Mockito.`when`((getWeatherForecastUseCase("Riyadh")))
        .thenReturn(
          flowOf(successResponse)
            .map { throw Exception() }
        )

      val job = launch {
        weatherDetailsViewModel.uiState.toList(testResults)
      }
      weatherDetailsViewModel.loadWeatherForecast("Riyadh")

      MatcherAssert.assertThat(testResults[0], IsInstanceOf.instanceOf(UiState.Init::class.java))
      MatcherAssert.assertThat(testResults[1], IsInstanceOf.instanceOf(UiState.Loading::class.java))
      MatcherAssert.assertThat(testResults[2], IsInstanceOf.instanceOf(UiState.Error::class.java))
      job.cancel()
    }

  @Test
  fun `when loadWeatherForecast() and getWeatherForecastUseCase() not return anything then return Empty state`() =
    runBlockingTest {
      Mockito.`when`((getWeatherForecastUseCase("Riyadh")))
        .thenReturn(flowOf())

      val job = launch {
        weatherDetailsViewModel.uiState.toList(testResults)
      }
      weatherDetailsViewModel.loadWeatherForecast("Riyadh")

      MatcherAssert.assertThat(testResults[0], IsInstanceOf.instanceOf(UiState.Init::class.java))
      MatcherAssert.assertThat(testResults[1], IsInstanceOf.instanceOf(UiState.Loading::class.java))
      MatcherAssert.assertThat(testResults[2], IsInstanceOf.instanceOf(UiState.Empty::class.java))
      job.cancel()
    }

}