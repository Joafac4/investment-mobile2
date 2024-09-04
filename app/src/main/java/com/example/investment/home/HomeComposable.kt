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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import com.example.investment.home.viewModel.StockRowModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investment.R
import com.example.investment.home.viewModel.GlobalQuoteContainer


@Composable
fun Home() {



    val buttons = listOf<HomeButton>(
        HomeButton(Icons.Filled.Check, trailingIcon = Icons.Filled.KeyboardArrowRight, title = "Simulate", onClick = {}),
        HomeButton(Icons.Filled.ShoppingCart, trailingIcon = Icons.Filled.KeyboardArrowRight, title = "Status", onClick = {})
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {

        Row (horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            buttons.forEach { button ->
                ButtonWithIcons(
                    leadingIcon = button.leadingIcon,
                    title = button.title,
                    trailingIcon = button.trailingIcon,
                    modifier = Modifier.weight(1f)
                    )
            }
        }

    }
}


@Composable
fun Title() {
    Surface(modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary
        ) {
        Text(
            text = stringResource(id = R.string.home_title),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
    }
}

@Composable
fun InvestmentCard(){
    Card(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp)
            .wrapContentHeight()
            .height(200.dp)

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.card_title),
                fontSize = 20.sp
            )
            Text(
                text = stringResource(id = R.string.card_subtitle),
                fontSize = 12.sp
            )
        }
        Row (modifier = Modifier.align(Alignment.End) ){
            Image(painter = painterResource(id = R.drawable.investment_card_img), contentDescription = "")
        }
        }
}

@Composable
fun ButtonWithIcons(
    leadingIcon: ImageVector,
    title: String,
    trailingIcon: ImageVector,
    modifier: Modifier = Modifier
) {
    Button(onClick = {}, modifier = Modifier
        .wrapContentHeight()
    ) {
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(imageVector = leadingIcon, contentDescription = "")
            Text(text = title)
            Icon(imageVector = trailingIcon, contentDescription = "")
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

@Composable
fun NewsFeed(){
    var isExpanded by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { isExpanded = !isExpanded },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "News Feed",style = MaterialTheme.typography.titleMedium)
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "@Marau2021")
                Text(text = "United Kingdom - 3 minutes ago")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                )
            }
        }
    }
}


@Composable
fun StockRowElement(globalQuoteContainer: GlobalQuoteContainer){
    Column {
        Text(
            text = globalQuoteContainer.globalQuote.name,
            fontSize = 15.sp
        )
        Text(
            text = globalQuoteContainer.globalQuote.price,
            fontSize = 20.sp
            )
        Text(
            text = "+" + globalQuoteContainer.globalQuote.change,
            fontSize = 20.sp,
            color = Color.Green
        )
    }
}

@Composable
fun NavigationTab(text : String){
    Tab(selected = true, onClick = { /*TODO*/ }){
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
        )
    }

}


@Composable
fun HomePage(
){
    val viewModel = hiltViewModel<StockRowModel>()
    val boxes by viewModel.box.collectAsState()

    Surface(onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column {
            Title()
            Spacer(modifier = Modifier.height(16.dp))
            Home()
            InvestmentCard()
            StockRow(boxes)
            NewsFeed()
        }
    }
}

data class HomeButton(
    val leadingIcon: ImageVector,
    val trailingIcon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

