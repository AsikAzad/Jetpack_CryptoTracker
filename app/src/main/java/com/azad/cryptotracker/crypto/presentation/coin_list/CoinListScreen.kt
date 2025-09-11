package com.azad.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.azad.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.azad.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.azad.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
){
    if (state.isLoading){
        //To display a circular progress indicator
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else{
        //To display the coin item list
        LazyColumn (
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(state.coins) { coinUi ->
                //Creating list item rows from provided data list
                CoinListItem(
                    coinUi = coinUi,
                    onClick = {/*TODO*/},
                    modifier = Modifier.fillMaxWidth()
                )
                //Adding a divider after each row
                HorizontalDivider()
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CoinListScreenPreview(){
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..100).map {                  //Creating a list with preview coin object
                    previewCoin.copy(id = it.toString())      //Providing unique id to the copy list
                }
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.background )
        )
    }
}