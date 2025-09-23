package com.azad.cryptotracker.core.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//Condition for content color
@Composable
fun getThemeContentColor(): Color {
    return if (isSystemInDarkTheme()){
        Color.White
    }else{
        Color.Black
    }
}