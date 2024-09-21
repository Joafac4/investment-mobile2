package com.example.investment.investment

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.investment.investment.viewModel.AnalyticsResponse
import com.example.investment.investment.viewModel.SimulationModel

@Preview
@Composable
fun SimulationHome() {
    val viewModel: SimulationModel = hiltViewModel<SimulationModel>()
    var selectedDate by remember { mutableStateOf("") } // Variable para almacenar la fecha seleccionada
    var selectedCompanies by remember { mutableStateOf(listOf<String>()) } // Variable para almacenar las empresas seleccionadas
    val analytics by viewModel.analytics.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            SelectSimulationDate(selectedDate) { newDate ->
                selectedDate = newDate
            }
            ShowInvestmentOptions(selectedCompanies) { newCompanies ->
                selectedCompanies = newCompanies
            }
            Button(
                onClick = {
                    viewModel.simulateInvestment(selectedDate, selectedCompanies.joinToString(","))
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Enviar")
            }
            ShowSimulationResult(analytics)
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectSimulationDate(selectedDate: String, onDateSelected: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text("DOI") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )


        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(30.dp)
                ) {
                    Column {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )


                        Button(
                            onClick = {
                                // Actualizar la fecha seleccionada y cerrar el DatePicker
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val newDate = convertMillisToDate(millis)
                                    onDateSelected(newDate)
                                }
                                showDatePicker = false
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(top = 16.dp)
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

// Función para convertir milisegundos a una fecha en formato legible (dd/MM/yyyy por ejemplo)
fun convertMillisToDate(millis: Long): String {
    val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(millis))
}



@Composable
fun ShowInvestmentOptions(selectedCompanies: List<String>, onCompaniesSelected: (List<String>) -> Unit) {
    val options = listOf("GOOGL", "AAPL", "IBM")
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = RectangleShape)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = if (selectedCompanies.isEmpty()) "Select options" else selectedCompanies.joinToString(", "),
            modifier = Modifier.padding(16.dp)
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                val isSelected = selectedCompanies.contains(option)
                DropdownMenuItem(
                    text = {
                        Row {
                            Text(option)
                            Spacer(Modifier.weight(1f))
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    onClick = {
                        val newCompanies = if (isSelected) {
                            selectedCompanies - option
                        } else {
                            if (selectedCompanies.size < 3) {
                                selectedCompanies + option
                            } else {
                                selectedCompanies
                            }
                        }
                        onCompaniesSelected(newCompanies)
                    }
                )
            }
        }
    }
}


@Composable
fun ShowSimulationGraph(){}

@Composable
fun ShowSimulationResult(analyticsResponse: AnalyticsResponse?) {
        val latestResults =
            analyticsResponse?.payload?.return_calculations?.mean?.runningMean?.map { (symbol, values) ->
                val latestDate = values.keys.maxByOrNull { it }
                val latestMean = latestDate?.let { values[it] }

                symbol to (latestDate to latestMean)
            }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Investment Results",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                latestResults?.forEach { (symbol, result) ->
                    val (latestDate, latestMean) = result
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$symbol (${latestDate ?: "No date"})",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "${
                                latestMean?.let {
                                    String.format(
                                        "%.2f",
                                        it * 100
                                    )
                                } ?: "No data"
                            }%",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

}


