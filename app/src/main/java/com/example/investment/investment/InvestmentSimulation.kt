package com.example.investment.investment

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.investment.R
import com.example.investment.data.SimulationResult
import com.example.investment.history.HistoryViewModel
import com.example.investment.investment.APIservice.HistoricalPriceResponse
import com.example.investment.investment.viewModel.SimulationModel
import com.example.investment.ui.theme.PurpleGrey80
import com.example.investment.ui.theme.historyTitle
import com.example.investment.ui.theme.investmentHomeCardTitle
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.content.Context
import androidx.compose.ui.platform.LocalContext


@Composable
fun SimulationHome(assets: List<String>) {
    val viewModel: SimulationModel = hiltViewModel<SimulationModel>()
    val loading: StateFlow<Boolean> = viewModel.loadingStockBox
    var selectedDate by remember { mutableStateOf("") }
    var selectedCompanies by remember { mutableStateOf(listOf<String>()) }
    val historical by viewModel.historical.collectAsState()
    var investmentAmount by remember { mutableStateOf(0.0) }
    val roomViewModel: HistoryViewModel = hiltViewModel<HistoryViewModel>()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column (modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            SelectSimulationDate(selectedDate) { newDate ->
                selectedDate = newDate
            }
            ShowInvestmentOptions(selectedCompanies,assets) { newCompanies ->
                selectedCompanies = newCompanies
            }
            ShowInvestmentAmount { newAmount ->
                investmentAmount = newAmount
            }
            Button(
                onClick = {
                    viewModel.clearSimulationResults()
                    selectedCompanies.forEach { selectedCompany ->
                        viewModel.simulateInvestment(selectedDate,selectedCompany )}
                },
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.sixteen))
                    .fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.investment_simulation_simulationButton))
            }
            ShowSimulationResult(historical, selectedDate, investmentAmount, roomViewModel,loading)
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectSimulationDate(selectedDate: String, onDateSelected: (String) -> Unit) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val todayMillis = System.currentTimeMillis()
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = { Text(stringResource(id = R.string.investment_simulation_datePicker_label)) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.investment_simulation_datePicker_iconDescription)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.simDate_height))
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = dimensionResource(id = R.dimen.simDate_height))
                        .shadow(elevation = dimensionResource(id = R.dimen.shadow))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(dimensionResource(id = R.dimen.box_shadow))
                ) {
                    Column {
                        DatePicker(
                            state = datePickerState,
                            showModeToggle = false
                        )

                        Button(
                            onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    // Validate that the selected date is today or earlier
                                    if (millis <= todayMillis) {
                                        val newDate = convertMillisToDate(millis)
                                        onDateSelected(newDate)
                                    } else {
                                        // Show error or handle future date selection
                                        Toast.makeText(context, context.getString(R.string.toast_simul_date),Toast.LENGTH_SHORT).show()
                                    }
                                }
                                showDatePicker = false
                            },
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(dimensionResource(id = R.dimen.sixteen))
                        ) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
    return formatter.format(java.util.Date(millis))
}


@Composable
fun ShowInvestmentAmount(onAmountChanged: (Double) -> Unit) {
    var inputText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = inputText,
        onValueChange = { newText ->
            inputText = newText
            val amount = newText.toDoubleOrNull() ?: 0.0  // Esto es para convertir el texto a Double
            onAmountChanged(amount)
        },
        label = { Text(stringResource(id = R.string.investment_simulation_investmentAmountInput_label)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.sixteen)),
        singleLine = true
    )
}



@Composable
fun ShowInvestmentOptions(selectedCompanies: List<String>, assets: List<String>,onCompaniesSelected: (List<String>) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = dimensionResource(id = R.dimen.border),
                color = Color.Gray,
                shape = RectangleShape
            )
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = if (selectedCompanies.isEmpty()) stringResource(id = R.string.investment_simulation_optionsTextfield) else selectedCompanies.joinToString(", "),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.sixteen))
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            assets.forEach { option ->
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
fun ShowSimulationResult(historicalPriceResponses: List<HistoricalPriceResponse>, selectedDate: String, investmentAmount: Double, roomViewModel: HistoryViewModel, loading: StateFlow<Boolean>){
    Column (modifier = Modifier.verticalScroll(rememberScrollState())){
        historicalPriceResponses.forEach {  ShowSimulationPerResult(
        historicalPriceResponse = it ,
        selectedDate = selectedDate,
        investmentAmount = investmentAmount,
        roomViewModel = roomViewModel,
        loading = loading
    )
        }
    }

}

@Preview
@Composable
fun SimulationError(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.sixteen)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.investment_simulation_error),
            fontSize = investmentHomeCardTitle,
            color = colorResource(id = R.color.red),
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.sixteen))
        )
        Text(
            text = stringResource(id = R.string.investment_simulation_error_date),
            fontSize = historyTitle,
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.tf))
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ShowSimulationPerResult(loading: StateFlow<Boolean>,historicalPriceResponse: HistoricalPriceResponse?, selectedDate: String, investmentAmount: Double, roomViewModel: HistoryViewModel) {
    var closeValueOnSelectedDate : Double = 0.0
    var closeValueToday : Double = 0.0
    val today = getCurrentDate()

    historicalPriceResponse?.historical?.forEach { historical ->
        if(historical.date == selectedDate) {
            closeValueOnSelectedDate = historical.close
        }
        if (historical.date == today) {
            closeValueToday = historical.close
        }
    }

    val earnings = calculateEarnings(closeValueOnSelectedDate,closeValueToday,investmentAmount)
    val earningsPercentage = calculateEarningsPercentage(closeValueOnSelectedDate,closeValueToday)
    val symbol = historicalPriceResponse?.symbol
    if (symbol == null) {
        return
    }
    if (closeValueOnSelectedDate == 0.0){
        return SimulationError()
    }


    if(loading.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.simDate_height))
                    .align(Alignment.Center),
                color = PurpleGrey80,
                trackColor = PurpleGrey80,
            )

        }
    }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.sixteen)),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.investment_simulation_card),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.spacer_8))
                )


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = earnings,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(end = dimensionResource(id = R.dimen.spacer_8)),
                        color = getResultColor(earnings)
                    )
                    Text(
                        text = earningsPercentage,
                        style = MaterialTheme.typography.titleLarge,
                        color = getResultColor(earningsPercentage)
                    )
                }
            }
        }

    val simulationResult = SimulationResult(
        resourceSimulated = symbol,
        simulationDate = today,
        simulationResult = earnings,
        simulationResultPercentage = earningsPercentage
    )
    roomViewModel.addSimulation(simulationResult)

}

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date())
}

fun calculateEarnings(investmentDateClose:Double, todayClose: Double, investmentAmount: Double) : String {
    val earnings =  investmentAmount * todayClose - investmentAmount * investmentDateClose
    return if(earnings > 0){
        "+ ${Math.round(earnings)}"
    }
    else{
        " ${Math.round(earnings)}"
    }
}

fun calculateEarningsPercentage(investmentDateClose:Double, todayClose: Double) : String {
    val eranings =    (todayClose * 100)/ investmentDateClose
    if (eranings > 100){
        return "+ %${eranings -100}"
    }
    else{
        return "- %${100 - eranings}"
    }

}

fun getResultColor(result: String): Color{
    if (result.get(0) == '+'){
        return Color.Green
    }
    return Color.Red
}