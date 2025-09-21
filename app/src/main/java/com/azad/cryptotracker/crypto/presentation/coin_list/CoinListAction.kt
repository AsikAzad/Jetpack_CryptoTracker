package com.azad.cryptotracker.crypto.presentation.coin_list

import com.azad.cryptotracker.crypto.presentation.models.CoinUi

//This bundles all the ui actions of a single screen (for MVI pattern)
sealed interface CoinListAction {
    //When user clicks on any coin
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
    //If user wants to refresh the list
    //We will not use this action for this app
//    data object OnRefresh: CoinListAction
}