package com.example.investment.history

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investment.data.SimulationHistoryDatabase
import com.example.investment.data.SimulationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @ApplicationContext val context: Context,
) : ViewModel() {

    val historyDatabase = SimulationHistoryDatabase.getDatabase(context)
    val simulationsList = historyDatabase.SimulationResultDao().getAllSimulations().asFlow()

    fun addSimulation(simulationResult: SimulationResult) {
        viewModelScope.launch {
            historyDatabase.SimulationResultDao().insert(simulationResult)
        }
    }
}
