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
import com.example.investment.profile.Profile

@Composable
fun NavHostComposable(innerPadding: PaddingValues, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = InvestmentScreen.Home.name,
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 10.dp)
    ) {
        composable(route = InvestmentScreen.Home.name) {
            HomePage()
        }
        composable(route = InvestmentScreen.Investments.name) {
            InvestmentHome(navController)
        }
        composable(route = InvestmentScreen.Profile.name) {
            Profile()
        }
        composable(route = InvestmentScreen.History.name) {
            SimulationHistory()
        }
        composable(route = InvestmentScreen.Simulation.name) {
            SimulationHome()
        }
    }
}