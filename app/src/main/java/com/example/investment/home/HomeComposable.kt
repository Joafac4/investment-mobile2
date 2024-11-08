package com.example.investment.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.example.investment.home.viewModel.StockRowModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.investment.R
import com.example.investment.home.viewModel.GlobalQuoteContainer
import com.example.investment.navigation.InvestmentScreen.*
import com.example.investment.ui.theme.homeCardSubtitleDimension
import com.example.investment.ui.theme.twentySPdimen
import com.example.investment.ui.theme.homeTitleDimension
import com.example.investment.ui.theme.twitterNameDimension

@Preview
@Composable
fun Title() {
        Text(
            text = stringResource(id = R.string.home_title),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.sixteen))
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = homeTitleDimension,
            fontFamily = FontFamily.Serif,
        )
}


@Composable
fun InvestmentCard(navController: NavHostController){
    val button = HomeButton(Icons.AutoMirrored.Filled.KeyboardArrowLeft,  title = "Simulate", onClick = {})
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.h_invcard_padding))
            .wrapContentHeight()
            .height(dimensionResource(id = R.dimen.hom_investmentCard_height)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )

    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.sixteen))
        ) {
            Text(
                text = stringResource(id = R.string.card_title),
                fontSize = twentySPdimen
            )
            Text(
                text = stringResource(id = R.string.card_subtitle),
                fontSize = homeCardSubtitleDimension
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.h_invcard_row_padding)),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,

            ){
            ButtonWithIcons(
                leadingIcon = button.leadingIcon,
                title = button.title,
                modifier = Modifier.weight(1f),
                navController = navController
            )
            Image(painter = painterResource(id = R.drawable.investment_card_img), contentDescription = "")
        }
        }
}


@Composable
fun ButtonWithIcons(
    leadingIcon: ImageVector,
    title: String,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Button(onClick = {navController.navigate(Investments.name)}, modifier = Modifier
        .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.purple_700)
        )
    ) {
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = leadingIcon, contentDescription = "")
            Text(text = title)
        }
    }
}

@Composable
fun StockRow(globalQuotescontainers : List<GlobalQuoteContainer>){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            globalQuotescontainers.forEach{
                globalQuoteContainer: GlobalQuoteContainer -> StockRowElement(globalQuoteContainer)
            }
        }

}

@Preview
@Composable
fun NewsFeed(){
    var isExpanded by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.sixteen))
                .clickable { isExpanded = !isExpanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.news_feed),style = MaterialTheme.typography.titleMedium)
            Icon(
                imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column (modifier = Modifier.verticalScroll(rememberScrollState()),){
                TweetColumn(
                    name = stringResource(id = R.string.news_user),
                    time = stringResource(id = R.string.news_time),
                    tweet = stringResource(id = R.string.news_tweet)

                )
                TweetColumn(
                    name = stringResource(id = R.string.john_Bogle),
                    time = stringResource(id = R.string.news_time),
                    tweet = stringResource(id = R.string.Bogle_tweet)
                )
                TweetColumn(
                    name = stringResource(id = R.string.musk), 
                    time = stringResource(id = R.string.SA),
                    tweet = stringResource(id = R.string.Musk_tweet))
                
            }
        }
    }
}

@Composable
fun TweetColumn(name:String, time:String, tweet: String){
    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.sixteen))) {
        Text(text = name,
            color = colorResource(id = R.color.strong_golden)
        )
        Text(text = time)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_8)))
        Text(
            text = tweet,
        )
    }
}


@Composable
fun StockRowElement(globalQuoteContainer: GlobalQuoteContainer){
    if(globalQuoteContainer.globalQuote != null){
    Column {
        Text(
            text = globalQuoteContainer.globalQuote.name,
            fontSize = twitterNameDimension
        )
        Text(
            text = globalQuoteContainer.globalQuote.price,
            fontSize = twentySPdimen
            )
        Text(
            text = globalQuoteContainer.globalQuote.change,
            fontSize = twentySPdimen,
            color = selectStockChangeColour(globalQuoteContainer.globalQuote.change)
        )
    }
    }
}


@Composable
fun HomePage(navController: NavHostController
){
    val viewModel = hiltViewModel<StockRowModel>()
    val boxes by viewModel.box.collectAsState()

    Surface(onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column  {
            Title()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.sixteen)))
            InvestmentCard(navController)
            StockRow(boxes)
            NewsFeed()
        }
    }
}



fun selectStockChangeColour(change: String): Color{
    val positiveOrNegative = change.get(0)
    if (positiveOrNegative == '-'){
        return Color.Red
    }
    return Color.Green
}

data class HomeButton(
    val leadingIcon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)
