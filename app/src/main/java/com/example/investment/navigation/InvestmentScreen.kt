package com.example.investment.navigation

enum class InvestmentScreen {
    Home,
    Profile,
    Notifications,
    Investments,
    Settings,
}

val basePages = listOf(
    InvestmentScreen.Home.name,
    InvestmentScreen.Investments.name,
    InvestmentScreen.Settings.name,
    InvestmentScreen.Notifications.name,
    InvestmentScreen.Profile.name,
)