package com.example.prc_pokemon.data.model

/** Clase de datos para localizaciones
 * @param locationsInfo : Para paginacion.
 * @param results : Lista de cada localizacion con sus datos.
 * */
data class Locations(
    val locationsInfo: LocationsInfo,
    val results: List<SingleLocation>
)

data class LocationsInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

/** Datos de localizacion individual. */
data class SingleLocation(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)