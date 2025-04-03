package com.example.prc_pokemon.ui.screens.mainScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prc_pokemon.R
import com.example.prc_pokemon.data.model.RickMortyModelList

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = viewModel()
) {
    val data by viewModel.state.collectAsState()

    when (data.isLoading) {
        true -> LoadingScreen(modifier = Modifier.fillMaxSize())
        false -> {
            if (data.error == "") {
                ListScreen(
                    modifier = Modifier.fillMaxSize(),
                    data = data.data
                )
            } else {
                NothingScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = data.error
                )
            }
        }
    }

}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Obteniendo datos...")
        CircularProgressIndicator()
    }
}

@Composable
private fun NothingScreen(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error al obtener datos: $message")
    }
}

@Composable
private fun ListScreen(
    modifier: Modifier = Modifier,
    data: List<RickMortyModelList>
) {

    val imgList = listOf(
        R.drawable.foto_rickmorty_personajes,
        R.drawable.foto_rickmorty_episodios,
        R.drawable.foto_rickmorty_episodios
    )
    LazyColumn {

        itemsIndexed(data) { index, item ->
            ItemCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                name = item.name,
                url = item.url,
                imgInt = imgList[index],
            )
        }
    }

}

@Composable
private fun ItemCard(
    modifier: Modifier = Modifier,
    name: String,
    url: String,
    @DrawableRes imgInt: Int
) {
    ElevatedCard(
        modifier = modifier,
        onClick = {
            println("Clicked.")
        },
        shape = CardDefaults.shape
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(imgInt), contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Text(
                text = name,
                fontStyle = FontStyle.Italic,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp)
            )
            Text(text = url, modifier = Modifier.padding(10.dp))
        }
    }
}
