package com.sdamashchuk.data.net.api

import com.sdamashchuk.data.net.dto.ForecastDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class OpenMeteoApi(private val httpClient: HttpClient) {

    private val hourlyTags = listOf(
        "temperature_2m",
        "relativehumidity_2m",
        "windspeed_10m",
        "precipitation_probability",
        "winddirection_10m",
        "weathercode"
    )
    private val dailyTags = listOf(
        "temperature_2m_min", "temperature_2m_max", "weathercode"
    )

    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): ForecastDTO = httpClient.get(BASE_URL) {
        parameter("latitude", latitude)
        parameter("longitude", longitude)
        parameter("current_weather", true.toString())
        parameter("timezone", "GMT")
        parameter("daily", dailyTags.joinToString(","))
    }.body()

    suspend fun getHourlyForecast(
        latitude: Double,
        longitude: Double
    ): ForecastDTO = httpClient.get(BASE_URL) {
        parameter("latitude", latitude)
        parameter("longitude", longitude)
        parameter("current_weather", true)
        parameter("timezone", "GMT")
        parameter("hourly", hourlyTags.joinToString(","))
    }.body()

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1/forecast/"
    }

}
