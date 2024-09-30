package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import com.example.investment.investment.SimulationHome

@Composable
fun ShowForexHome(){
    val forex = listOf(stringResource(id = R.string.asset_forex_EUR), stringResource(id = R.string.asset_forex_USD))
    return SimulationHome(forex)
}