package com.example.prc_pokemon.ui.screens.EpisodesListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
fun EpisodesListAppMain(
    modifier: Modifier = Modifier,
    viewModel: EpisodesListScreenViewModel = viewModel(),
    onSelectedEpisode: (episodeId: Int) -> Unit
) {
    val state = viewModel.uiState

    when (state) {
        is EpisodesListScreenUiState.Success -> {
            EpisodesResult(data = state.episodes.results) { episodeId ->
                onSelectedEpisode(episodeId)
            }
        }

        is EpisodesListScreenUiState.Loading -> LoadingScreen()
        is EpisodesListScreenUiState.Error -> ErrorMessageScreen(message = "Error obteniendo datos.")
    }
}

@Composable
private fun EpisodesResult(
    modifier: Modifier = Modifier, data: List<SingleEpisode>,
    onSelectedEpisode: (episodeId: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(data) { index, item ->
            EpisodesCardItem(modifier = Modifier.clickable {
                onSelectedEpisode(item.id)
            }, episode = item)
        }
    }
}

@Composable
private fun EpisodesCardItem(modifier: Modifier = Modifier, episode: SingleEpisode) {

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título del episodio + ícono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Go",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }
    }
}