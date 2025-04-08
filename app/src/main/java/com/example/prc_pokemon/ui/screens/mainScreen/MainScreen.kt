package com.example.prc_pokemon.ui.screens.mainScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prc_pokemon.R
import com.example.prc_pokemon.data.model.PrincipalList
import com.example.prc_pokemon.ui.utils.ErrorMessageScreen
import com.example.prc_pokemon.ui.utils.LoadingScreen

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel(),
    onGoToScreen: (Int) -> Unit
) {
    val data by viewModel.state.collectAsState()

    when (data.isLoading) {
        true -> LoadingScreen(modifier = Modifier.fillMaxSize())
        false -> {
            if (data.error == "") {
                ListOfCards(
                    modifier = Modifier.fillMaxSize(),
                    data = data.data,
                    onGoToScreen
                )
            } else {
                ErrorMessageScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = data.error
                )
            }
        }
    }

}

@Composable
private fun ListOfCards(
    modifier: Modifier = Modifier,
    data: List<PrincipalList>,
    onGoToScreen: (Int) -> Unit
) {

    val imgList = listOf(
        R.drawable.img_characters,
        R.drawable.img_locations,
        R.drawable.img_episodes
    )
    LazyColumn {
        itemsIndexed(data) { index, item ->
            ItemCard(
                modifier = Modifier
                    .padding(20.dp),
                name = item.name,
                imgInt = imgList[index],
                onGoToScreen = {
                    onGoToScreen(index)
                },
            )
        }
    }

}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    name: String,
    @DrawableRes imgInt: Int,
    onGoToScreen: () -> Unit
) {

    ElevatedCard(
        modifier = modifier,
        onClick = {
            onGoToScreen()
        },
        shape = CardDefaults.shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
            ) {
                Image(
                    painter = painterResource(imgInt), contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.FillWidth
                )
            }

            Text(
                text = name,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
private fun Cardpreview(modifier: Modifier = Modifier) {
    val res = R.drawable.img_episodes
    ItemCard(
        modifier = Modifier.fillMaxWidth(),
        name = "Episodios",
        imgInt = res,
        onGoToScreen = TODO(),
    )
}