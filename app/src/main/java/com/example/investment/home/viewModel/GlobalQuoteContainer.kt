package com.example.investment.home.viewModel

import com.google.gson.annotations.SerializedName

data class GlobalQuoteContainer(
    @SerializedName("Global Quote") val globalQuote: GlobalQuote
)

data class GlobalQuote(
    @SerializedName("01. symbol") val name: String,
    @SerializedName("05. price") val price : String,
    @SerializedName("09. change") val change : String
)



