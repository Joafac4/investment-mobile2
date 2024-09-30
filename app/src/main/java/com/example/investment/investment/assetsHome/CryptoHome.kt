package com.example.investment.investment.assetsHome

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import com.example.investment.investment.SimulationHome

@Composable
fun ShowCryptoHome(){
    val crypto = listOf(stringResource(id = R.string.asset_crypto_BTC), stringResource(id = R.string.asset_crypto_ETH))
    return SimulationHome(crypto)
}