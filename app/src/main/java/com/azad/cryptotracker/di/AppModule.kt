package com.azad.cryptotracker.di

import com.azad.cryptotracker.core.data.networking.HttpClientFactory
import com.azad.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.azad.cryptotracker.crypto.domain.CoinDataSource
import com.azad.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module{
    //This will provide http client
    single{ HttpClientFactory.create(CIO.create()) }

    //This will provide data source
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    //This will provide viewmodel
    viewModelOf(::CoinListViewModel)
}