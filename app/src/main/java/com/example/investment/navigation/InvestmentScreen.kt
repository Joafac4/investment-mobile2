package com.example.investment.navigation

enum class InvestmentScreen {
    Home,
    Profile,
    Notifications,
    Investments,
    History,
    Simulation
}

val basePages = listOf(
    InvestmentScreen.Home.name,
    InvestmentScreen.Investments.name,
    InvestmentScreen.History.name,
    InvestmentScreen.Notifications.name,
    InvestmentScreen.Profile.name,
    InvestmentScreen.Simulation.name,
)