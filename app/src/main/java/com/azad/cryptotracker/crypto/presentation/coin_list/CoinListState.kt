package com.azad.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.azad.cryptotracker.crypto.presentation.models.CoinUi

//This class is created to provide the whole UI state objects as a bundle (MVI pattern)
@Immutable  //To tell compiler that this class will not change, if changes then the whole instance needs to be replaced
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
