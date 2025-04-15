package com.example.prc_pokemon.ui.screens.CharactersListScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.SingleCharacter
import com.example.prc_pokemon.ui.utils.BarraDeBusqueda
import com.example.prc_pokemon.ui.utils.ErrorMessageScreen
import com.example.prc_pokemon.ui.utils.LoadingScreen
import kotlinx.coroutines.delay

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CharacterListScreenAppMain(
    modifier: Modifier = Modifier,
    viewModel: CharacterListScreenViewModel = viewModel()
) {
    val characterScreenUiState = viewModel.charactersUiState

    Column(
        modifier = modifier
    ) {
        when (characterScreenUiState) {
            is CharacterScreenUiState.Success -> Result(
                modifier = Modifier.fillMaxSize(),
                data = characterScreenUiState.charactersList,
                onNextPage = { viewModel.getCharactersNextPage() },
                onPreviousPage = { viewModel.getCharactersPreviousPage() },
                onNullPrevStatusPage = if (viewModel.previousStatePage.value.isNullOrEmpty()) false else true,
                nPage = viewModel.nPage.value
            )

            is CharacterScreenUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            is CharacterScreenUiState.Error -> ErrorMessageScreen(
                modifier = Modifier.fillMaxSize(),
                message = "Error al obtener datos."
            )
        }
    }
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    data: Characters,
    onNullPrevStatusPage: Boolean,
    nPage: Int,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    //Observa los cambios en la lista.
    var listaObserver by remember { mutableStateOf(data.results) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        BarraDeBusqueda { query ->
            var personajeFiltrado =
                listaObserver.filter { it.name.lowercase().contains(query.lowercase()) }
            listaObserver = emptyList()
            listaObserver = personajeFiltrado
            when {
                personajeFiltrado.isEmpty() -> {
                    message = "No se ha encontrado al personaje."
                    expanded = true
                    listaObserver = data.results
                }

                query.isEmpty() -> {
                    message = "No ha ingresado ningun nombre."
                    expanded = true
                    listaObserver = data.results
                }
            }
        }
        if (expanded) {
            LaunchedEffect(Unit) {
                delay(2000)
                expanded = false
            }
        }
        //Texto que aparece y luego desaparece.
        AnimatedVisibility(expanded) {
            Text(text = message)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            itemsIndexed(listaObserver) { index, item ->
                CharacterCard(
                    character = item
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Previous Page.
            Row(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .clickable(
                        enabled = onNullPrevStatusPage,
                        onClick = {
                            onPreviousPage()
                        }
                    )
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Pagina anterior."
                )
                Text(text = "Anterior")
            }
            Text(text = nPage.toString())
            //Next Page.
            Row(
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp)
                    .clickable {
                        onNextPage()
                    }
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Siguiente")
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = "Pagina siguiente."
                )
            }
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
                    .height(300.dp)
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
                    StatusColorAndText(status = character.status)
                    Text("Tipo: ${character.type.ifEmpty { "Desconocido" }}")
                    Text("Origen: ${character.origin.name}")
                    Text("Última ubicación: ${character.location.name}")
                }
            }
        }
    }
}

@Composable
private fun StatusColorAndText(status: String) {
    var colorStatus: Color = Color.Blue
    when (status) {
        "Alive" -> colorStatus = Color.Green
        "Dead" -> colorStatus = Color.Red
        "unknown" -> colorStatus = Color.Gray
    }
    Row() {
        CircleWithChangingColor(colorStatus, modifier = Modifier.padding(end = 5.dp))
        Text(text = status)
    }
}

/** Cambia el color de un circulo al crearse.
 * @param color Recibe el color */
@Composable
private fun CircleWithChangingColor(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(20.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        drawCircle(
            color = color,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension / 2
        )
    }
}