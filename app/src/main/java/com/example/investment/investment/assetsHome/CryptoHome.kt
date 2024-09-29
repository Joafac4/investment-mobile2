package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import com.example.investment.investment.SimulationHome

@Composable
fun ShowCryptoHome(){
    val crypto = listOf("BTC", "ETH")
    return SimulationHome(crypto)
}