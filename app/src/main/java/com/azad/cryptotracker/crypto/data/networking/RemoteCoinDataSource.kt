package com.azad.cryptotracker.crypto.data.networking

import com.azad.cryptotracker.core.data.networking.constructUrl
import com.azad.cryptotracker.core.data.networking.safeCall
import com.azad.cryptotracker.core.domain.util.NetworkError
import com.azad.cryptotracker.core.domain.util.Result
import com.azad.cryptotracker.core.domain.util.map
import com.azad.cryptotracker.crypto.data.mappers.toCoin
import com.azad.cryptotracker.crypto.data.mappers.toCoinPrice
import com.azad.cryptotracker.crypto.data.networking.dto.CoinHistoryResponseDto
import com.azad.cryptotracker.crypto.data.networking.dto.CoinResponseDto
import com.azad.cryptotracker.crypto.data.util.convertZoneTimeToMillis
import com.azad.cryptotracker.crypto.domain.Coin
import com.azad.cryptotracker.crypto.domain.CoinDataSource
import com.azad.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
): CoinDataSource {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {

        //Requesting the data from the api
        return safeCall<CoinResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            //Convert the response to business object
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError> {

        //To convert the zone datetime to unified milliseconds
        val startMillis = convertZoneTimeToMillis(start)
        val endMillis = convertZoneTimeToMillis(end)

        //Requesting the data from the api
        return safeCall<CoinHistoryResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ){
                //Here we can add query parameters
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            //Convert the response to business object
            response.data.map{it.toCoinPrice()}
        }
    }
}