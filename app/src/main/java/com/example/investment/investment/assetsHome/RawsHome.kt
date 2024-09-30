package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import com.example.investment.investment.SimulationHome

@Composable
fun ShowRawsHome(){
    val raws = listOf(stringResource(id = R.string.asset_raws_ALIUSD),stringResource(id = R.string.asset_raws_CLUSD))
    return SimulationHome(raws)
}