package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import com.example.investment.investment.SimulationHome

@Composable
fun ShowForexHome(){
    val forex = listOf("EUR","USD")
    return SimulationHome(forex)
}