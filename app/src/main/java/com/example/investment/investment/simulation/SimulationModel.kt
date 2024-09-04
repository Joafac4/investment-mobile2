package com.example.investment.investment.simulation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.sql.Date
import javax.inject.Inject
import java.time.LocalDateTime

@HiltViewModel
class SimulationModel @Inject constructor() : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun addFriend(inversionDate: Date) {
        val today = LocalDateTime.now()

    }
}