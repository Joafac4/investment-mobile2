package com.example.investment.investment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.investment.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.investment.navigation.InvestmentScreen
import com.example.investment.ui.theme.historyTitle
import com.example.investment.ui.theme.invTitle
import com.example.investment.ui.theme.investmentHomeCardTitle


@Preview
@Composable
fun PreviewIHome(){
    InvestmentCard(investmentType = "crypto", onNavigate = { /*TODO*/ }, urls = listOf(1) )
}

@Composable
fun InvestmentHome(navController: NavHostController){
    val forexUrls = listOf(R.drawable.dolar,R.drawable.euros,R.drawable.yenes)
    val stockUrsl = listOf(R.drawable.google,R.drawable.msft_logo,R.drawable.applelogo)
    val rawsUrls = listOf(R.drawable.gold,R.drawable.oil,R.drawable.trigo)
    val cryptoUrls = listOf(R.drawable.ethereum,R.drawable.bitcoin,R.drawable.solana)
    Surface(modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .height(dimensionResource(id = R.dimen.oneFif))
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
            ){
            Text(text = stringResource(id = R.string.investment_home),
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.inv_home_title))
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = invTitle,
                fontFamily = FontFamily.Serif,
            )
            InvestmentCard(
                stringResource(id = R.string.investment_card_name_stock),
                onNavigate = { navController.navigate(InvestmentScreen.Stock.name)},
                stockUrsl,
                    )
            InvestmentCard(
                stringResource(id = R.string.investment_card_name_crypto),
                onNavigate = { navController.navigate(InvestmentScreen.Crypto.name)},
                cryptoUrls,
                    )
            InvestmentCard(
                stringResource(id = R.string.investment_card_name_forex),
                    onNavigate = { navController.navigate(InvestmentScreen.Forex.name)},
                forexUrls,
                )
            InvestmentCard(
                stringResource(id = R.string.investment_card_name_raws),
                    onNavigate = { navController.navigate(InvestmentScreen.Raws.name)},
                rawsUrls,
                )
        }
    }
}


@Composable
fun InvestmentCard(investmentType: String, onNavigate: () -> Unit, urls : List<Int>){
    Card(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.spacer_8))
            .padding(horizontal = dimensionResource(id = R.dimen.spacer_8))
            .wrapContentHeight()
            .height(dimensionResource(id = R.dimen.simulation_investmentCard_height))
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.superLightGolden)
        ),
        border = BorderStroke(dimensionResource(id = R.dimen.border), colorResource(id = R.color.purple_700))
    )  {
        Row (horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = investmentType,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_5)),
                fontSize = investmentHomeCardTitle,
                fontFamily = FontFamily.Serif)
        }
        Column(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.hegiht_80))
                .padding(dimensionResource(id = R.dimen.padding_5)),
            verticalArrangement = Arrangement.Bottom,

            ){
            Row (horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()){
                    urls.forEach{it -> CardImage(imageUrl = it)}
                }
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_6)))
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Text(
                    text = stringResource(id = R.string.investment_card_simulation),
                    color = colorResource(id = R.color.purple_700),
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.clickable {
                        onNavigate()
                    }
                )
            }
        }
    }
}

@Composable
fun CardImage(imageUrl: Int ){
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.box_asset_img))
            .clip(CircleShape)
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(imageUrl),
            contentDescription = "Euro Dollar",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}