package com.azad.cryptotracker.crypto.domain

//This is the business object
data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)

//To calculate absolute change in price
fun Coin.calculateAbsoluteChange24Hr(): Double {
    return this.priceUsd * (this.changePercent24Hr / 100)
}