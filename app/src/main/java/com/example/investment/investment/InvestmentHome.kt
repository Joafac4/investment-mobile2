package com.example.investment.investment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.investment.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource

@Preview
@Composable
fun InvestmentHome(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Simulate Investments")
        Column(modifier = Modifier.height(150.dp),
            verticalArrangement = Arrangement.Center
            ){
            InvestmentCard("Stocks")
            InvestmentCard("Crypto")
            InvestmentCard("National Treasures")
        }
    }
}


@Composable
fun InvestmentCard(investmentType: String){
    Card(onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 8.dp)
            .wrapContentHeight()
            .height(100.dp)
            .fillMaxWidth()) {
        Text(text = investmentType, modifier = Modifier.padding(5.dp))
        Column(modifier = Modifier
            .height(80.dp)
            .padding(5.dp),
            verticalArrangement = Arrangement.Bottom,

        ){
            Text(text = stringResource(id = R.string.investment_card_simulation),
                color = colorResource(id = R.color.purple_500),
                )
        }
    }
}