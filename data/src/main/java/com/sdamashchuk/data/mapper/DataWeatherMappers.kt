package com.sdamashchuk.data.mapper

import com.sdamashchuk.data.net.dto.ForecastDTO
import com.sdamashchuk.model.Weather
import com.sdamashchuk.model.WeatherData
import com.sdamashchuk.model.WeatherType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private data class TimeAssociatedWeatherData(
    val time: LocalDateTime,
    val data: WeatherData
)

private data class DateAssociatedWeatherData(
    val date: LocalDate,
    val data: WeatherData
)

fun ForecastDTO.toWeather(): Weather {
    return Weather(
        current = WeatherData(
            temperature = currentWeather.temperature,
            windSpeed = currentWeather.windSpeed,
            windDirection = currentWeather.windDirection.roundToInt(),
            humidity = hourly.relativeHumidity2m.first(),
            precipitationProbability = hourly.precipitationProbability.first(),
            type = WeatherType.fromWMO(currentWeather.weatherCode)
        ),
        hourly = List(hourly.time.size) { index ->
            val time = hourly.time[index]
            val temperature = hourly.temperature2m[index]
            val windSpeed = hourly.windSpeed10m[index]
            val windDirection = hourly.windDirection10m[index]
            val humidity = hourly.relativeHumidity2m[index]
            val precipitationProbability = hourly.precipitationProbability[index]
            val wmoCode = hourly.weatherCode[index]
            TimeAssociatedWeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                data = WeatherData(
                    temperature = temperature,
                    windSpeed = windSpeed,
                    windDirection = windDirection,
                    humidity = humidity,
                    precipitationProbability = precipitationProbability,
                    type = WeatherType.fromWMO(wmoCode)
                )
            )
        }.associateBy {
            it.time
        }.mapValues {
            it.value.data
        },
        daily = List(hourly.time.size) { index ->
            val time = hourly.time[index]
            val temperature = hourly.temperature2m[index]
            val windSpeed = hourly.windSpeed10m[index]
            val windDirection = hourly.windDirection10m[index]
            val humidity = hourly.relativeHumidity2m[index]
            val precipitationProbability = hourly.precipitationProbability[index]
            val wmoCode = hourly.weatherCode[index]
            DateAssociatedWeatherData(
                date = LocalDate.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                data = WeatherData(
                    temperature = temperature,
                    windSpeed = windSpeed,
                    windDirection = windDirection,
                    humidity = humidity,
                    precipitationProbability = precipitationProbability,
                    type = WeatherType.fromWMO(wmoCode)
                )
            )
        }.associateBy {
            it.date
        }.mapValues {
            it.value.data
        },
        latitude = latitude,
        longitude = longitude,
        timezone = timezone
    )
}