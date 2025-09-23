package com.azad.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.azad.cryptotracker.R
import com.azad.cryptotracker.core.presentation.util.getThemeContentColor
import com.azad.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.azad.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.azad.cryptotracker.ui.theme.CryptoTrackerTheme
import com.azad.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
){
    //Theme content color
    val contentColor = getThemeContentColor()

    if (state.isLoading){
        //To display a circular progress indicator
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        //To make sure a coin is selected
    } else if (state.selectedCoin != null){
        val coin = state.selectedCoin
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //Display coin logo
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            //Display coin name
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor
            )
            //Display coin symbol
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor
            )

            //For dynamic row adjustment
            FlowRow (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                //Display market cap block
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock)
                )
                //Display price block
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar)
                )

                //Condition for price change color
                val isPositive = coin.changePercent24Hr.value > 0.0
                val contentColor = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                }else{
                    MaterialTheme.colorScheme.error
                }
                //Display price change block
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = coin.absoluteChange24Hr.formatted,
                    icon = if (isPositive) {
                        ImageVector.vectorResource(R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }
        }
    }
}



@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview(){
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListState(
                selectedCoin = previewCoin
            ),
            modifier = Modifier.background(
                MaterialTheme.colorScheme.background
            )
        )
    }
}