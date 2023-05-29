package com.sdamashchuk.data.net.api

import io.ktor.client.HttpClient

class OpenMeteoApi(private val httpClient: HttpClient) {

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1/"
    }
}
