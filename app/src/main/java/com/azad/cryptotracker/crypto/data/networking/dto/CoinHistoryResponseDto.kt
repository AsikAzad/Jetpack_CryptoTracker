package com.azad.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable


//Request response model returns a data field which contains the list of objects
@Serializable
data class CoinHistoryResponseDto(
    val data : List<CoinPriceDto>
)
