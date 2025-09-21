package com.azad.cryptotracker.core.data.networking

import com.azad.cryptotracker.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine){
            //Install logging plugin to get all network logs
            install(Logging){
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }

            //This will ignore other data fields received from request response which are not included in our objects
            install(ContentNegotiation){
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                //Specifying default request type
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer ${BuildConfig.API_KEY}")
            }
        }
    }
}