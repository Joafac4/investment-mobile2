package com.example.investment.investment.APIservice


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