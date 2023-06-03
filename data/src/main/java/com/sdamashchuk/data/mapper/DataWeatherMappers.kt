package com.sdamashchuk.data.mapper

import com.sdamashchuk.data.net.dto.ForecastDTO
import com.sdamashchuk.model.CurrentWeatherData
import com.sdamashchuk.model.DailyForecast
import com.sdamashchuk.model.DailyWeatherData
import com.sdamashchuk.model.HourlyForecast
import com.sdamashchuk.model.HourlyWeatherData
import com.sdamashchuk.model.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun ForecastDTO.toDailyForecast(): DailyForecast {
    return DailyForecast(
        daily = if (daily != null) List(daily.time.size) { index ->
            val time = daily.time[index]
            val temperatureMin = daily.temperature2mMin[index]
            val temperatureMax = daily.temperature2mMax[index]
            val windSpeedMax = daily.windspeed10mMax[index]
            val precipitationProbabilityMean = daily.precipitationProbabilityMean[index]
            val wmoCode = daily.weatherCode[index]
            DailyWeatherData(
                date = LocalDate.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureMin = temperatureMin,
                temperatureMax = temperatureMax,
                windSpeedMax = windSpeedMax,
                precipitationProbabilityMean = precipitationProbabilityMean,
                type = WeatherType.fromWMO(wmoCode)
            )
        } else listOf(),
        latitude = latitude,
        longitude = longitude,
        timezone = timezone
    )
}

fun ForecastDTO.toHourlyForecast(): HourlyForecast {
    return HourlyForecast(
        current = if (currentWeather != null) CurrentWeatherData(
            isDay = currentWeather.isDay == 1,
            temperature = currentWeather.temperature,
            windSpeed = currentWeather.windSpeed,
            windDirection = currentWeather.windDirection.roundToInt(),
            humidity = hourly?.relativeHumidity2m?.firstOrNull(),
            precipitationProbability = hourly?.precipitationProbability?.firstOrNull(),
            type = WeatherType.fromWMO(currentWeather.weatherCode)
        ) else null,
        hourly = if (hourly != null) List(hourly.time.size) { index ->
            val time = hourly.time[index]
            val temperature = hourly.temperature2m[index]
            val windSpeed = hourly.windSpeed10m[index]
            val windDirection = hourly.windDirection10m[index]
            val humidity = hourly.relativeHumidity2m[index]
            val precipitationProbability = hourly.precipitationProbability[index]
            val wmoCode = hourly.weatherCode[index]
            HourlyWeatherData(
                dateTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperature = temperature,
                windSpeed = windSpeed,
                windDirection = windDirection,
                humidity = humidity,
                precipitationProbability = precipitationProbability,
                type = WeatherType.fromWMO(wmoCode)
            )
        } else listOf(),
        latitude = latitude,
        longitude = longitude,
        timezone = timezone
    )
}