package com.example.investment.investment.APIservice

import com.google.gson.annotations.SerializedName

data class HistoricalPriceResponse(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("historical") val historical: List<Historical>
)

data class Historical(
    @SerializedName("date") val date: String,
    @SerializedName("open") val open: Double,
    @SerializedName("close") val close: Double,
)