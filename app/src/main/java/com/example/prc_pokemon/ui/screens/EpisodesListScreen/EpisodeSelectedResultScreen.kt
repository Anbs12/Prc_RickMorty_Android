package com.example.prc_pokemon.ui.screens.EpisodesListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prc_pokemon.data.model.SingleEpisode
import com.example.prc_pokemon.ui.utils.ErrorMessageScreen
import com.example.prc_pokemon.ui.utils.LoadingScreen

@Composable
fun EpisodeSelectedResultScreen(
    modifier: Modifier = Modifier,
    viewModel: EpisodeSelectedScreenViewModel = viewModel(),
    id: Int
) {
    val fillmaxSize = Modifier.fillMaxSize()
    val uiState = viewModel.uiState

    when (uiState) {
        is EpisodeSelectedScreenUIState.Error -> ErrorMessageScreen(
            modifier = fillmaxSize,
            message = uiState.message
        )

        EpisodeSelectedScreenUIState.Loading -> {
            LoadingScreen(fillmaxSize)
            viewModel.initData(id)
        }

        is EpisodeSelectedScreenUIState.Success -> ResultScreen(fillmaxSize, uiState.data)
    }
}

@Composable
private fun ResultScreen(
    modifier: Modifier = Modifier,
    singleEpisode: SingleEpisode
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(text = "Nombre: ${singleEpisode.name}")
            Text(text = "Fecha de Emisión: ${singleEpisode.air_date}")
            Text(text = "ID: ${singleEpisode.id}")
            Text(text = "Episodio: ${singleEpisode.episode}")
            Text(text = "Creado: ${singleEpisode.created}")
            Text(text = "URL: ${singleEpisode.url}")
            /*Text(text = "Personajes:")
            //Itera sobre la lista de personajes
            singleEpisode.characters.forEach { character ->
                Text(text = "- $character", modifier = Modifier.padding(start = 16.dp))
            }*/
        }
    }
}