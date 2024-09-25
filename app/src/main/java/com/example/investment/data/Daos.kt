package com.example.investment.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface SimulationResultDao {
    @Insert
    suspend fun insert(simulationResult: SimulationResult)

    @Query("SELECT * FROM simulationResult")
    fun getAllSimulations(): LiveData<List<SimulationResult>>

}