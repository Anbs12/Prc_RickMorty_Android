package com.example.prc_pokemon.ui.utils

import android.os.Build
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.prc_pokemon.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Pantalla que muestra el estado de Error al no recibir respuesta.
 *
 *  @param message String que recibe el mensaje de error.
 * */
@Composable
fun ErrorMessageScreen(
    modifier: Modifier = Modifier,
    message: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $message")
    }
}

/** Pantalla de carga. */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Obteniendo datos...")
        CircularProgressIndicator()
    }
}

/**Formatea la fecha a formato local
 * @param isoDate Ingreso del formato de la fecha*/
fun formatDate(isoDate: String): String {
    return try {
        val parser = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        val date = OffsetDateTime.parse(isoDate, parser)
        formatter.format(date)
    } catch (e: Exception) {
        Log.e("", "Error: ${e.message}")
        isoDate
    }
}

/** Barra de busqueda a implementar.
 *
 * Si la version de la API del dispositivo es mayor que 31, utilizará SearchBar de Jetpack Compose
 * de lo contrario utilizará un OutlinedTextField como campo de búsqueda.
 *
 * @param onQuerySearch Funcion con retorno de string que representa
 * la palabra que el usuario quiere encontrar.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDeBusqueda(
    modifier: Modifier = Modifier,
    onQuerySearch: (query: String) -> Unit
) {

    val apiLevel31 = Build.VERSION_CODES.S
    val actualAndroidVersion = Build.VERSION.SDK_INT
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    if (actualAndroidVersion >= apiLevel31) {
        Box(
            modifier
                .fillMaxWidth()
                .semantics { isTraversalGroup = true }
                .padding(bottom = 10.dp)) {
            SearchBar(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        query = text,
                        onQueryChange = {
                            text = it
                            onQuerySearch(it) //Al devolver nada, se reiniciara la lista.
                        },
                        onSearch = {
                            expanded = false
                            //manda query de busqueda.
                            onQuerySearch(text)
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("Nombre de personaje aqui") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
                    )
                },
                expanded = false,
                onExpandedChange = { expanded = it }
            ) {/*Searchbar llaves.*/ }
        }
    } else {
        var text by rememberSaveable { mutableStateOf("") }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    onQuerySearch(it)
                },
                label = {
                    Text("Buscar")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(R.string.busqueda_personajes)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        //Mandamos query de busqueda.
                        onQuerySearch(text)
                    }
                )
            )
        }
    }
}
