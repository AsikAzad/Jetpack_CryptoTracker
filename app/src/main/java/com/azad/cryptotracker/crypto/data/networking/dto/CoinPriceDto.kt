package com.azad.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable

//Request response data model
@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)
