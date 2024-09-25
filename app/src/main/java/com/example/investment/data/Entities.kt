package com.example.investment.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "simulationResult")
data class SimulationResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val resourceSimulated: String,
    val simulationResult: String,
    val simulationResultPercentage: String,
    val simulationDate: String,
)