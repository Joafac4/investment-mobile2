package com.example.investment.home.APIservice

import retrofit.Call
import com.example.investment.home.viewModel.GlobalQuoteContainer
import retrofit.http.GET
import retrofit.http.Query

interface APIservice {


    @GET("query")
    fun getGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "IBM",
        @Query("apikey") apikey: String = "1BVR2NJBN38KOSJB"
    ): Call<GlobalQuoteContainer>


    @GET("query")
    fun getSecondGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "GOOGL",
        @Query("apikey") apikey: String = "1BVR2NJBN38KOSJB"
    ): Call<GlobalQuoteContainer>

    @GET("query")
    fun getThirdGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "AAPL",
        @Query("apikey") apikey: String = "1BVR2NJBN38KOSJB"
    ): Call<GlobalQuoteContainer>

}