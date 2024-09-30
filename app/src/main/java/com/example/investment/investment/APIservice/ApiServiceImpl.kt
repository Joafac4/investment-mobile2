package com.example.investment.investment.APIservice

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import javax.inject.Inject
import retrofit.Call
import retrofit.Response
import retrofit.Callback

class ApiServiceImpl @Inject constructor() {

    fun getHistosicalResponse(context: Context, onSuccess: (HistoricalPriceResponse) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit, symbol: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.financial_modeling_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: ApiServiceSecondAPI = retrofit.create(ApiServiceSecondAPI::class.java)

        val call: Call<HistoricalPriceResponse> = service.getHistoricalPrice(symbol = symbol)


        call.enqueue(object : Callback<HistoricalPriceResponse> {
            override fun onResponse(response: Response<HistoricalPriceResponse>?, retrofit: Retrofit?) {
                val rawJson = response?.errorBody()?.string() ?: response?.body().toString()
                loadingFinished()
                if(response?.isSuccess == true) {
                    val historical_price : HistoricalPriceResponse = response.body()
                    onSuccess(historical_price)
                } else {
                    onFailure(Exception("Bad request"))
                }
            }

            override fun onFailure(t: Throwable?) {
                Toast.makeText(context, "Can't get analytics", Toast.LENGTH_SHORT).show()
                onFail()
                loadingFinished()
            }
        })
    }


}