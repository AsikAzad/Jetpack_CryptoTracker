package com.azad.cryptotracker.crypto.domain

import com.azad.cryptotracker.core.domain.util.NetworkError
import com.azad.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}