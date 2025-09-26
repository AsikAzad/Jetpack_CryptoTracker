package com.azad.cryptotracker.crypto.data.mappers

import com.azad.cryptotracker.crypto.data.networking.dto.CoinDto
import com.azad.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.azad.cryptotracker.crypto.data.util.convertMillisToZoneTime
import com.azad.cryptotracker.crypto.domain.Coin
import com.azad.cryptotracker.crypto.domain.CoinPrice
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

//To convert the response to business object
fun CoinDto.toCoin(): Coin{
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

//To convert the response to business object
fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = convertMillisToZoneTime(time)
    )
}