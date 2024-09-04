package com.example.investment.home.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investment.home.APIservice.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockRowModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiServiceImpl: ApiServiceImpl,
) : ViewModel() {

    private val _loadingStockBox = MutableStateFlow(false)
    val loadingStockBox = _loadingStockBox.asStateFlow()

    private val _box= MutableStateFlow(listOf<GlobalQuoteContainer>())
    val box = _box.asStateFlow()

    private val _showRetry = MutableStateFlow(false)
    val showRetry = _showRetry.asStateFlow()

    init {
        loadStockBoxes()
    }

    fun retryLoadingBoxes() {
        loadStockBoxes()
    }

    private fun loadStockBoxes() {
        _loadingStockBox.value = true
        apiServiceImpl.getGlobalQuote(
            context = context,
            onSuccess = {
                viewModelScope.launch {
                    _box.emit(listOf(it))
                }
                _showRetry.value = false
            },
            onFail = {
                _showRetry.value = true
            },
            loadingFinished = {
                _loadingStockBox.value = false
            }
        )
    }



}