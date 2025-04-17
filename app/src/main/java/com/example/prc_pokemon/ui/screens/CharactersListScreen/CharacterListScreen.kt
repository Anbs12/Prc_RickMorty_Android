package com.example.prc_pokemon.ui.screens.CharactersListScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.SingleCharacter
import com.example.prc_pokemon.ui.utils.BarraDeBusqueda
import com.example.prc_pokemon.ui.utils.ErrorMessageScreen
import com.example.prc_pokemon.ui.utils.LoadingScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CharacterListScreenAppMain(
    modifier: Modifier = Modifier,
    viewModel: CharacterListScreenViewModel = viewModel()
) {
    val characterScreenUiState = viewModel.charactersUiState

    /**Focus del compose actual.*/
    val focusManager: FocusManager = LocalFocusManager.current

    /**Controlador del teclado del dispositivo actual.*/
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    var expanded by remember { mutableStateOf(viewModel.isSearchBarUsed) }

    Column(
        modifier = modifier
            .clickable(onClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
            }),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarraDeBusqueda { query ->
            when {
                query.isNotEmpty() -> {
                    viewModel.getFilteredCharacter(query)
                }

                query.isNullOrEmpty() -> {
                    viewModel.getCharacters()
                }
            }
        }
        Column(modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            when (characterScreenUiState) {
                is CharacterScreenUiState.Success -> {
                    Result(
                        data = characterScreenUiState.charactersList,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is CharacterScreenUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
                is CharacterScreenUiState.Error -> ErrorMessageScreen(
                    modifier = Modifier.fillMaxSize(),
                    message = "Error al obtener datos."
                )
            }
        }
        if (!expanded.value) {
            BottomPagination(
                nPage = viewModel.nPage.value,
                isNullNextStatusPage = !viewModel.nextStatePage.value.isNullOrEmpty(),
                isNullPrevStatusPage = !viewModel.previousStatePage.value.isNullOrEmpty(),
                onNextPage = { viewModel.getCharactersNextPage() },
                onPreviousPage = { viewModel.getCharactersPreviousPage() }
            )
        }
    }
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    data: Characters
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

/** Botones de seleccion de pagina. */
@Composable
private fun BottomPagination(
    nPage: Int,
    isNullPrevStatusPage: Boolean,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit,
    isNullNextStatusPage: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Previous Page.
        Button(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .padding(5.dp)
                .background(MaterialTheme.colorScheme.background),
            enabled = isNullPrevStatusPage,
            onClick = { onPreviousPage() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Pagina anterior."
            )
        }
        //Middle Text.
        Text(text = nPage.toString())
        //Next Page.
        Button(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .padding(5.dp)
                .background(MaterialTheme.colorScheme.background),
            enabled = isNullNextStatusPage,
            onClick = { onNextPage() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "Pagina siguiente."
            )
        }
    }
}