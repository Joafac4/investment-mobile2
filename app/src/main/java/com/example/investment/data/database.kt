package com.example.investment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [SimulationResult::class], version = 1)
abstract class SimulationHistoryDatabase : RoomDatabase() {
    abstract fun SimulationResultDao(): SimulationResultDao


    companion object {
        @Volatile
        private var INSTANCE: SimulationHistoryDatabase? = null


        fun getDatabase(context: Context): SimulationHistoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SimulationHistoryDatabase::class.java,
                    "simulation_history_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}