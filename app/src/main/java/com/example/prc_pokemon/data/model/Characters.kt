package com.example.prc_pokemon.data.model

/*
Formatos de datos para obtener todos los Personajes.
 */

//Lista completa de personajes.
data class Characters(
    val info: Info,
    val results: List<SingleCharacter>
)

//Para paginacion de los personajes obtenidos.
data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

//Datos de cada personaje individual.
data class SingleCharacter(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

//Ultima vez visto del personaje.
data class Location(
    val name: String,
    val url: String
)

//Origen del personaje.
data class Origin(
    val name: String,
    val url: String
)