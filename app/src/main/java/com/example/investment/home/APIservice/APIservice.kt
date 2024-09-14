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
        @Query("apikey") apikey: String = "OVQSF2G3RPX7NGS8"
    ): Call<GlobalQuoteContainer>


    @GET("query")
    fun getSecondGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "GOOGL",
        @Query("apikey") apikey: String = "OVQSF2G3RPX7NGS8"
    ): Call<GlobalQuoteContainer>

    @GET("query")
    fun getThirdGlobalQuote(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String = "AAPL",
        @Query("apikey") apikey: String = "OVQSF2G3RPX7NGS8"
    ): Call<GlobalQuoteContainer>

}