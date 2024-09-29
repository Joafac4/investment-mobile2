package com.example.investment.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.investment.history.SimulationHistory
import com.example.investment.home.HomePage
import com.example.investment.investment.InvestmentHome
import com.example.investment.investment.SimulationHome
import com.example.investment.investment.assetsHome.ShowCryptoHome
import com.example.investment.investment.assetsHome.ShowForexHome
import com.example.investment.investment.assetsHome.ShowRawsHome
import com.example.investment.investment.assetsHome.ShowStockHome

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = InvestmentScreen.Home.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 10.dp)
    ) {
        composable(route = InvestmentScreen.Home.name) {
            HomePage(navController)
        }
        composable(route = InvestmentScreen.Investments.name) {
            InvestmentHome(navController)
        }
        composable(route = InvestmentScreen.History.name) {
            SimulationHistory()
        }
        composable(route = InvestmentScreen.Crypto.name){
            ShowCryptoHome()
        }
        composable(route = InvestmentScreen.Stock.name){
            ShowStockHome()
        }
        composable(route = InvestmentScreen.Raws.name){
            ShowRawsHome()
        }
        composable(route = InvestmentScreen.Forex.name){
            ShowForexHome()
        }
    }
}