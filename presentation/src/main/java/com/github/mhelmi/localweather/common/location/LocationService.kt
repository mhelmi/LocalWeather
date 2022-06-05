package com.github.mhelmi.localweather.common.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammad Helmi on 04/6/2022.
 * m.helmi.khalil@gmail.com
 */
interface LocationService {
  fun getCurrentLocation(): Flow<Location>
}