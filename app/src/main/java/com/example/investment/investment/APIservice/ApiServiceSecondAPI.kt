package com.example.investment.investment.APIservice

import com.example.investment.investment.viewModel.AnalyticsResponse
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Query

interface ApiServiceSecondAPI{

@GET("{symbol}")
fun getHistoricalPrice(
    @Path("symbol") symbol: String,
    @Query("apikey") apikey: String = "IIEPG38xbRJSjVdHxAiSE1OLfiWWYeYC",
): Call<HistoricalPriceResponse>


}