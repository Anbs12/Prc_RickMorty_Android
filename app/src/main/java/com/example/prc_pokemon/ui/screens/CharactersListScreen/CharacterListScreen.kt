package com.example.prc_pokemon.ui.screens.CharactersListScreen

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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.SingleCharacter
import com.example.prc_pokemon.ui.screens.mainScreen.LoadingScreen
import com.example.prc_pokemon.ui.screens.mainScreen.NothingScreen

@Composable
fun CharacterListScreenApp(
    modifier: Modifier = Modifier,
    viewModel: CharacterListScreenViewModel = viewModel()
) {

    val characterScreenUiState = viewModel.charactersUiState

    Column(
        modifier = modifier
    ) {
        when (characterScreenUiState) {
            is CharacterScreenUiState.Success -> Result(data = characterScreenUiState.charactersList)
            is CharacterScreenUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is CharacterScreenUiState.Error -> NothingScreen(
                modifier = Modifier.fillMaxSize(),
                message = "Error al obtener datos."
            )
        }
    }
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    data: Characters
) {
    LazyColumn {
        itemsIndexed(data.results) { index, item ->
            CharacterCard(
                character = item
            )
        }
    }
}

@Composable
private fun CharacterCard(modifier: Modifier = Modifier, character: SingleCharacter) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Imagen
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fila para nombre + ícono desplegable
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier
                        .rotate(if (isExpanded) 180f else 0f)
                        .size(24.dp)
                )
            }

            // Información adicional si está expandido
            AnimatedVisibility(isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Género: ${character.gender}")
                    Text("Especie: ${character.species}")
                    Text("Estado: ${character.status}")
                    Text("Tipo: ${character.type.ifEmpty { "Desconocido" }}")
                    Text("Origen: ${character.origin.name}")
                    Text("Última ubicación: ${character.location.name}")
                }
            }
        }
    }
}

