package com.example.investment.investment.viewModel

import com.google.gson.annotations.SerializedName

data class AnalyticsResponse(
    @SerializedName("payload") val payload: Payload
)

data class Payload(
    @SerializedName("RETURNS_CALCULATIONS") val return_calculations: Return_calulations
)

data class Return_calulations(
    @SerializedName("MEAN") val mean : Mean
)

data class Mean(
    @SerializedName("RUNNING_MEAN") val runningMean: Map<String, Map<String, Double>>,
)
