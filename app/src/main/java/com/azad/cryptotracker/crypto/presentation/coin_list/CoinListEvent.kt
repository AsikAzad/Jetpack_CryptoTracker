package com.azad.cryptotracker.crypto.presentation.coin_list

import com.azad.cryptotracker.core.domain.util.NetworkError


//This sends one time events to UI like toast message
sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}