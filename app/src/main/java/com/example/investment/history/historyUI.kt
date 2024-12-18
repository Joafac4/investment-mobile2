package com.example.investment.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.investment.data.SimulationResult
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.investment.R
import com.example.investment.ui.theme.historyCardItems
import com.example.investment.ui.theme.historyTitle


@Composable
fun SimulationHistory() {
    val historyViewModel = hiltViewModel<HistoryViewModel>()
    val simulationsList by historyViewModel.simulationsList.collectAsState(initial = listOf())
    val searchQuery by historyViewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_5))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacer_8))
    ) {
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { query -> historyViewModel.updateSearchQuery(query) }
        )
        simulationsList.forEach { simulation ->
            SimulationHistoryCard(simulationResult = simulation) }
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        label = { Text(stringResource(id = R.string.historial_filtro)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding))
    )
}


@Composable
fun SimulationHistoryCard(simulationResult: SimulationResult) {
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_shape)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding)),
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.shadow))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.sixteen))
        ) {
            Text(
                text = simulationResult.simulationDate,
                fontWeight = FontWeight.Bold,
                fontSize = historyTitle,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorResource(id = R.color.black)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_8)))
            Text(
                text = "${stringResource(id = R.string.historial_assetSimulado)}: ${simulationResult.resourceSimulated}",
                fontSize = historyCardItems,
                modifier = Modifier.align(Alignment.Start),
                color = colorResource(id = R.color.black)
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacer)))
            Text(
                text = "${stringResource(id = R.string.historial_resultadonominal)}: ${simulationResult.simulationResult}",
                fontSize = historyCardItems,
                modifier = Modifier.align(Alignment.Start),
                color = colorResource(id = R.color.black)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacer)))
            Text(
                text = "${stringResource(id = R.string.historial_resultadoPorcentual)}: ${simulationResult.simulationResultPercentage}",
                fontSize = historyCardItems,
                modifier = Modifier.align(Alignment.Start),
                color = colorResource(id = R.color.black)
            )
        }
    }
}

