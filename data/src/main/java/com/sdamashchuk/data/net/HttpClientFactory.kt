package com.sdamashchuk.data.net

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.android.BuildConfig
import timber.log.Timber

class HttpClientFactory {

    @OptIn(ExperimentalSerializationApi::class)
    fun createHttpClient(context: Context): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    prettyPrint = BuildConfig.DEBUG
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            socketTimeoutMillis = requestTimeoutMillis
            connectTimeoutMillis = requestTimeoutMillis
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    if (message.isNotEmpty()) {
                        Timber.v(message)
                    }
                }
            }
            level = LogLevel.INFO
        }
    }
}
