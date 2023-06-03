package com.sdamashchuk.common.ui.mapper

import com.sdamashchuk.common.R
import com.sdamashchuk.common.ui.model.CurrentWeatherDataUIO
import com.sdamashchuk.common.ui.model.DailyWeatherDataUIO
import com.sdamashchuk.common.ui.model.HourlyWeatherDataUIO
import com.sdamashchuk.model.CurrentWeatherData
import com.sdamashchuk.model.DailyWeatherData
import com.sdamashchuk.model.HourlyWeatherData
import com.sdamashchuk.model.WeatherType
import java.time.LocalDateTime
import java.time.LocalTime

fun CurrentWeatherData.toCurrentWeatherDataUIO(): CurrentWeatherDataUIO = CurrentWeatherDataUIO(
    isDay = isDay,
    temperature = temperature,
    windSpeed = windSpeed,
    windDirection = windDirection,
    humidity = humidity,
    precipitationProbability = precipitationProbability,
    description = type.description,
    iconRes = type.toIconRes(isDay)
)

fun List<HourlyWeatherData>.toHourlyWeatherDataUIOList(): List<HourlyWeatherDataUIO> =
    this.map { it.toHourlyWeatherDataUIO() }

fun HourlyWeatherData.toHourlyWeatherDataUIO(): HourlyWeatherDataUIO = HourlyWeatherDataUIO(
    dateTime = dateTime,
    temperature = temperature,
    windSpeed = windSpeed,
    windDirection = windDirection,
    humidity = humidity,
    precipitationProbability = precipitationProbability,
    description = type.description,
    iconRes = type.toIconRes(dateTime.isDaytime())
)

fun List<DailyWeatherData>.toDailyWeatherDataUIOList(): List<DailyWeatherDataUIO> =
    this.map { it.toDailyWeatherDataUIO() }

fun DailyWeatherData.toDailyWeatherDataUIO(): DailyWeatherDataUIO = DailyWeatherDataUIO(
    date = date,
    temperatureMin = temperatureMin,
    temperatureMax = temperatureMax,
    windSpeedMax = windSpeedMax,
    precipitationProbabilityMean = precipitationProbabilityMean,
    description = type.description,
    iconRes = type.toIconRes(true)
)

private fun WeatherType.toIconRes(isDay: Boolean): Int = if (isDay) {
    when (this) {
        WeatherType.ClearSky, WeatherType.MainlyClear -> R.raw.clear_day
        WeatherType.PartlyCloudy -> R.raw.partly_cloudy_day
        WeatherType.Overcast -> R.raw.overcast_day
        WeatherType.Foggy, WeatherType.DepositingRimeFog -> R.raw.fog_day
        WeatherType.LightDrizzle, WeatherType.ModerateDrizzle -> R.raw.drizzle_day
        WeatherType.DenseDrizzle -> R.raw.dense_drizzle_day
        WeatherType.LightFreezingDrizzle, WeatherType.SlightSnowShowers -> R.raw.freezing_rain_day
        WeatherType.SlightRain, WeatherType.ModerateRain, WeatherType.SlightRainShowers, WeatherType.ModerateRainShowers -> R.raw.rain_day
        WeatherType.HeavyRain, WeatherType.ViolentRainShowers -> R.raw.heavy_rain_day
        WeatherType.HeavyFreezingRain, WeatherType.HeavySnowShowers, WeatherType.DenseFreezingDrizzle -> R.raw.heavy_freezing_rain_day
        WeatherType.SlightSnowFall, WeatherType.ModerateSnowFall -> R.raw.snow_day
        WeatherType.HeavySnowFall -> R.raw.heavy_snow_day
        WeatherType.SnowGrains -> R.raw.snow_grain
        WeatherType.ModerateThunderstorm -> R.raw.thunderstorm_day
        WeatherType.SlightHailThunderstorm -> R.raw.hail_day
        WeatherType.HeavyHailThunderstorm -> R.raw.heavy_hail_day
    }
} else {
    when (this) {
        WeatherType.ClearSky, WeatherType.MainlyClear -> R.raw.clear_night
        WeatherType.PartlyCloudy -> R.raw.partly_cloudy_night
        WeatherType.Overcast -> R.raw.overcast_night
        WeatherType.Foggy, WeatherType.DepositingRimeFog -> R.raw.fog_night
        WeatherType.LightDrizzle, WeatherType.ModerateDrizzle -> R.raw.drizzle_night
        WeatherType.DenseDrizzle -> R.raw.dense_drizzle_night
        WeatherType.LightFreezingDrizzle, WeatherType.SlightSnowShowers -> R.raw.freezing_rain_night
        WeatherType.SlightRain, WeatherType.ModerateRain, WeatherType.SlightRainShowers, WeatherType.ModerateRainShowers -> R.raw.rain_night
        WeatherType.HeavyRain, WeatherType.ViolentRainShowers -> R.raw.heavy_rain_night
        WeatherType.HeavyFreezingRain, WeatherType.HeavySnowShowers, WeatherType.DenseFreezingDrizzle -> R.raw.heavy_freezing_rain_night
        WeatherType.SlightSnowFall, WeatherType.ModerateSnowFall -> R.raw.snow_night
        WeatherType.HeavySnowFall -> R.raw.heavy_snow_night
        WeatherType.SnowGrains -> R.raw.snow_grain
        WeatherType.ModerateThunderstorm -> R.raw.thunderstorm_night
        WeatherType.SlightHailThunderstorm -> R.raw.hail_night
        WeatherType.HeavyHailThunderstorm -> R.raw.heavy_hail_night
    }
}

fun LocalDateTime.isDaytime(): Boolean {
    val time = this.toLocalTime()
    val startOfDay = LocalTime.of(4, 0) // Assuming day starts at 6:00 AM
    val endOfDay = LocalTime.of(21, 0) // Assuming day ends at 6:00 PM
    return time in startOfDay..endOfDay
}