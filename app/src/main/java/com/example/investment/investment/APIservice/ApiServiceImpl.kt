package com.example.investment.investment.APIservice

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.investment.R
import com.example.investment.investment.viewModel.AnalyticsResponse
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import javax.inject.Inject
import retrofit.Call
import retrofit.Response
import retrofit.Callback

class ApiServiceImpl @Inject constructor() {

    fun getAnalytics(context: Context, onSuccess: (AnalyticsResponse) -> Unit, onFail: () -> Unit, loadingFinished: () -> Unit, range: String, symbols: String) {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(
                context.getString(R.string.alphavantage_url)
            )
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()

        val service: APIservice = retrofit.create(APIservice::class.java)

        val call: Call<AnalyticsResponse> = service.getAnalytycs(range = range, symbols = symbols)

        Log.i("range", range )
        Log.i("symbols", symbols)

        call.enqueue(object : Callback<AnalyticsResponse> {
            override fun onResponse(response: Response<AnalyticsResponse>?, retrofit: Retrofit?) {
                val rawJson = response?.errorBody()?.string() ?: response?.body().toString()
                Log.i("Raw JSON Response", rawJson)
                Log.i("simul Response", "${response?.body()}")
                loadingFinished()
                if(response?.isSuccess == true) {
                    val analytcis: AnalyticsResponse = response.body()
                    onSuccess(analytcis)
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