package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import com.example.investment.investment.SimulationHome

@Composable
fun ShowRawsHome(){
    val raws = listOf("ALIUSD","CLUSD")
    return SimulationHome(raws)
}