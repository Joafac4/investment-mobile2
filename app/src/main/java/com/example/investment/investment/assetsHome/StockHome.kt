package com.example.investment.investment.assetsHome

import android.hardware.biometrics.BiometricManager.Strings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import com.example.investment.investment.SimulationHome

@Composable
fun ShowStockHome(){
    val stocks = listOf(stringResource(id = R.string.asset_stock_GOOGL), stringResource(id = R.string.asset_stock_AAPL), stringResource(id = R.string.asset_stock_IBM))
    return SimulationHome(stocks)
}