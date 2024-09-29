package com.example.investment.investment.assetsHome

import android.hardware.biometrics.BiometricManager.Strings
import androidx.compose.runtime.Composable
import com.example.investment.investment.SimulationHome

@Composable
fun ShowStockHome(){
    val stocks = listOf("GOOGL", "AAPL", "IBM")
    return SimulationHome(stocks)
}