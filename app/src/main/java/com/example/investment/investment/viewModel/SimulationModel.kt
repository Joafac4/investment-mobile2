package com.example.investment.investment.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investment.investment.APIservice.ApiServiceImpl
import com.example.investment.investment.APIservice.HistoricalPriceResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SimulationModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiServiceImpl: ApiServiceImpl
) : ViewModel() {

    private val _loadingStockBox = MutableStateFlow(false)
    val loadingStockBox = _loadingStockBox.asStateFlow()

    private val _historical = MutableStateFlow(listOf<HistoricalPriceResponse>())
    val historical = _historical.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()


    fun retrySimulation() {
        simulateInvestment("1","IBM")
    }

    fun simulateInvestment(date: String, symbol: String) {
        _loadingStockBox.value = true
        apiServiceImpl.getHistosicalResponse(
            context = context,
            onSuccess = { result ->
                viewModelScope.launch {
                    _historical.value = _historical.value + result
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingStockBox.value = false
            },
            symbol = symbol
        )
    }



}