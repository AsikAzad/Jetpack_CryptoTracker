@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.azad.cryptotracker.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azad.cryptotracker.core.presentation.util.ObserveAsEvents
import com.azad.cryptotracker.core.presentation.util.toString
import com.azad.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListScreen
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel


//Using adaptive navigation library to navigate between screens in adaptive way
//This Pane is treat as single screen that's why we used same viewmodel for both screens
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    //Here we handle observed one time events sent from viewmodel and show it to ui
    val context = LocalContext.current
    ObserveAsEvents(events = viewModel.events) { event ->
        when(event){
            is CoinListEvent.Error -> {

                //Show toast message to ui
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    //This navigator will navigate between screens
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()

    NavigableListDetailPaneScaffold(
        navigator = navigator,
        modifier = modifier,
        listPane = {
            //CoinListScreen is the list pane
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when(action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            //CoinDetailScreen is the detail pane
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
    )
}