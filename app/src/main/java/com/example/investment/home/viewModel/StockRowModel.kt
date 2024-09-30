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


    private val _box = MutableStateFlow<List<GlobalQuoteContainer>>(emptyList())
    val box = _box.asStateFlow()

    init {
        loadStockBoxes()
    }

    private fun loadStockBoxes() {
        apiServiceImpl.getGlobalQuote(
            context = context,
            onSuccess = { result ->
                viewModelScope.launch {
                    _box.emit(_box.value + result)
                }
            },
            onFail = {
            },
            loadingFinished = {
            }
        )
    }



}