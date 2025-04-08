package com.example.prc_pokemon.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

/**Formatea la fecha a formato LATAM
 * @param isoDate Ingreso del formato de la fecha*/
fun formatDate(isoDate: String): String {
    return try {
        val parser = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
        val date = OffsetDateTime.parse(isoDate, parser)
        formatter.format(date)
    } catch (e: Exception) {
        isoDate
    }
}