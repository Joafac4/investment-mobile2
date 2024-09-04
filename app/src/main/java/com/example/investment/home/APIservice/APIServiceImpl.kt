package com.example.investment.home.APIservice

import android.content.Context
import android.widget.Toast
import com.example.investment.R
import com.example.investment.home.viewModel.GlobalQuoteContainer
import retrofit.Call
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import javax.inject.Inject
import retrofit.Callback
import retrofit.Response

class ApiServiceImpl @Inject constructor() {

    fun getGlobalQuote(context: Context, onSuccess: (GlobalQuoteContainer) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.alphavantage_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: APIservice = retrofit.create(APIservice::class.java)

        val call: Call<GlobalQuoteContainer> = service.getGlobalQuote()
        val call1: Call<GlobalQuoteContainer> = service.getSecondGlobalQuote()
        val call2: Call<GlobalQuoteContainer> = service.getThirdGlobalQuote()
        val callList = listOf(call,call1,call2)

        enqueCalls(
            calls = callList,
            context = context,
            onSuccess = onSuccess,
            onFail = onFail,
            loadingFinished = loadingFinished
        )

    }

    private fun enqueCalls(calls : List<Call<GlobalQuoteContainer>>, context: Context, onSuccess: (GlobalQuoteContainer) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit ){
        calls.forEach { it -> it.enqueue(object : Callback<GlobalQuoteContainer> {
            override fun onResponse(response: Response<GlobalQuoteContainer>?, retrofit: Retrofit?) {
                loadingFinished()
                if(response?.isSuccess == true) {
                    val globalQuoteContainer = response.body()
                    onSuccess(globalQuoteContainer)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get StockValues", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        }) }
    }




}