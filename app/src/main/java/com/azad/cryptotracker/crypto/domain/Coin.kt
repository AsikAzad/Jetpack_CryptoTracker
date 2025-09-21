package com.azad.cryptotracker.crypto.domain

//This is the business object
data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsed: Double,
    val changePercent24Hr: Double
)
