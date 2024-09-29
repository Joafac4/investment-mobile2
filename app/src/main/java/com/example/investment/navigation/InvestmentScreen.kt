package com.example.investment.navigation

enum class InvestmentScreen {
    Home,
    Profile,
    Notifications,
    Investments,
    History,
    Crypto,
    Raws,
    Stock,
    Forex
}

val basePages = listOf(
    InvestmentScreen.Home.name,
    InvestmentScreen.Investments.name,
    InvestmentScreen.History.name,
    InvestmentScreen.Notifications.name,
    InvestmentScreen.Profile.name,
    InvestmentScreen.Crypto.name,
    InvestmentScreen.Stock.name,
    InvestmentScreen.Raws.name,
    InvestmentScreen.Forex.name
)