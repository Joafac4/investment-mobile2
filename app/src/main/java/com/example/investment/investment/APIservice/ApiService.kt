package com.example.investment.investment.APIservice

import com.example.investment.investment.viewModel.AnalyticsResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

interface APIservice {


    @GET("query")
    fun getAnalytycs(
        @Query("function") function: String = "ANALYTICS_SLIDING_WINDOW",
        @Query("SYMBOLS") symbols: String,
        @Query("apikey") apikey: String = "OVQSF2G3RPX7NGS8",
        @Query("RANGE") range: String,
        @Query("WINDOW_SIZE") windowSize: Int = 20,
        @Query("INTERVAL") interval: String = "DAILY",
        @Query("CALCULATIONS") calculations : String = "MEAN"
    ): Call<AnalyticsResponse>


}