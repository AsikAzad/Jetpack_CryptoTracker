package com.azad.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azad.cryptotracker.core.domain.util.onError
import com.azad.cryptotracker.core.domain.util.onSuccess
import com.azad.cryptotracker.crypto.domain.CoinDataSource
import com.azad.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
): ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart { loadCoins() }    //We will load our initial data when flow starts collection
        .stateIn(                   //It makes the normal flow to a state flow
            viewModelScope,
            //It will keep active the flow collection for 5 sec after the last subscriber to avoid re-collection this flow for device configuration change
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )


    //Channel is like shared flow but it doesn't cached the values and don't recall when any new subscriber arrives
    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()


    //Function to deal with the user actions on ui
    fun onAction(action: CoinListAction){
        when(action){
            is
            CoinListAction.OnCoinClick -> {
                _state.update {
                    it.copy(
                        selectedCoin = action.coinUi
                    )
                }
            }
//            CoinListAction.OnRefresh -> {
//                loadCoins()
//            }
        }
    }

    //Function to load coins
    private fun loadCoins(){
        viewModelScope.launch {

            //Initially we will activate the loading state
            _state.update { it.copy(
                isLoading = true
            ) }

            //Here we will request data then update the state based on the response
            coinDataSource
                .getCoins()
                //If the request is successful and returns data
                .onSuccess { coins ->
                    _state.update { it.copy(
                        //After data fetch deactivate loading state
                        isLoading = false,
                        //Here we will convert business object model to UI object model
                        coins = coins.map { it.toCoinUi() }
                    ) }
                }
                //If the request returns error
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    //Sends errors to the channel to observe in the ui
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}