package com.example.investment.history

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investment.data.SimulationHistoryDatabase
import com.example.investment.data.SimulationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    val historyDatabase = SimulationHistoryDatabase.getDatabase(context)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val simulationsList = historyDatabase.SimulationResultDao().getAllSimulations()
        .asFlow()
        .combine(_searchQuery) { list, query ->
            if (query.isBlank()) list
            else list.filter { it.resourceSimulated.contains(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addSimulation(simulationResult: SimulationResult) {
        viewModelScope.launch {
            historyDatabase.SimulationResultDao().insert(simulationResult)
        }
    }
}
