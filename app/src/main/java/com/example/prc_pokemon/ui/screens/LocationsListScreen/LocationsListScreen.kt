package com.example.prc_pokemon.ui.screens.LocationsListScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prc_pokemon.data.model.SingleLocation
import com.example.prc_pokemon.ui.utils.ErrorMessageScreen
import com.example.prc_pokemon.ui.utils.LoadingScreen
import com.example.prc_pokemon.ui.utils.formatDate

@Composable
fun LocationsAppMain(
    modifier: Modifier = Modifier,
    viewModel: LocationsListScreenViewModel = viewModel()
) {

    val state = viewModel.uiState

    when (state) {
        is LocationsListScreenUIState.Success -> {
            ResultScreen(modifier = Modifier.fillMaxSize(), data = state.locations.results)
        }

        is LocationsListScreenUIState.Loading -> LoadingScreen()
        is LocationsListScreenUIState.Error -> {
            ErrorMessageScreen(
                modifier = Modifier.fillMaxSize(),
                message = "Error al obtener datos"
            )
        }
    }
}

@Composable
private fun ResultScreen(
    modifier: Modifier = Modifier,
    data: List<SingleLocation>
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(data) { index, items ->
            LocationsCard(modifier = Modifier.fillMaxSize(), data = items)
        }
    }
}

@Composable
private fun LocationsCard(
    modifier: Modifier = Modifier,
    data: SingleLocation
) {
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título y flecha
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier
                        .rotate(if (isExpanded) 180f else 0f)
                        .size(24.dp)
                )
            }

            AnimatedVisibility(isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tipo: ${data.type}")
                    Text("Dimensión: ${data.dimension}")
                    Text("Cantidad de residentes: ${data.residents.size}")
                    Text("Creado: ${formatDate(data.created)}")
                }
            }
        }
    }
}




