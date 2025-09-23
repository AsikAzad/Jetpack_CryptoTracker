package com.azad.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.azad.cryptotracker.crypto.domain.Coin
import com.azad.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.azad.cryptotracker.crypto.domain.calculateAbsoluteChange24Hr
import java.text.NumberFormat
import java.util.Locale

//This class is created to provide all necessary UI data to UI screen (converted data from data sources)
data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val absoluteChange24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int
)

//This class is created to convert a double number to a UI displayable number
data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

//This function converts a double number to a UI displayable number
fun Double.toDisplayableNumber(): DisplayableNumber{
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumIntegerDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}

//This function converts a business object to a UI object
fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayableNumber(),
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        absoluteChange24Hr = calculateAbsoluteChange24Hr().toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

