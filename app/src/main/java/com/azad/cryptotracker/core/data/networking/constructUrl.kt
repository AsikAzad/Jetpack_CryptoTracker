package com.azad.cryptotracker.core.data.networking

import com.azad.cryptotracker.BuildConfig

//Utility function to generate proper url for network call
fun constructUrl(url: String): String{
    return when{
        url.contains(BuildConfig.BASE_URL) -> url
        url.startsWith("/") -> BuildConfig.BASE_URL + url.drop(1)
        else -> BuildConfig.BASE_URL + url
    }
}