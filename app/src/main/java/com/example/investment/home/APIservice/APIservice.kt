package com.example.investment.home.APIservice

import retrofit.Call
import com.example.investment.home.viewModel.GlobalQuoteContainer
import retrofit.http.GET
import retrofit.http.Query

interface APIservice {


    @GET("function=GLOBAL_QUOTE")
    fun getGlobalQuote(
        @Query("symbol") symbol: String = "IBM",
        @Query("apikey") apikey: String = "595T7TD5E78XXRFV"
    ): Call<GlobalQuoteContainer>


    @GET("function=GLOBAL_QUOTE")
    fun getSecondGlobalQuote(
        @Query("symbol") symbol: String = "GOOGLE",
        @Query("apikey") apikey: String = "595T7TD5E78XXRFV"
    ): Call<GlobalQuoteContainer>

    @GET("function=GLOBAL_QUOTE")
    fun getThirdGlobalQuote(
        @Query("symbol") symbol: String = "APPLE",
        @Query("apikey") apikey: String = "595T7TD5E78XXRFV"
    ): Call<GlobalQuoteContainer>

}