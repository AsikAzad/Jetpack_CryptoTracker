package com.azad.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable


//Request response object returns a data field which contains the list of objects
@Serializable
data class CoinResponseDto(
    val data: List<CoinDto>
)
