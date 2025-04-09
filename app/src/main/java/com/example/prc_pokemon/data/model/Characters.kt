package com.example.prc_pokemon.data.model

/*
Formatos de datos para obtener todos los Personajes.
 */

/** Clase de datos para personajes.
 * @param results Lista con informacion de cada personaje.*/
data class Characters(
    val info: CharactersInfo,
    val results: List<SingleCharacter>
)

/** Para paginacion de los personajes obtenidos. */
data class CharactersInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

/** Datos de cada personaje individual.*/
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

/** Ultima vez visto del personaje.*/
data class Location(
    val name: String,
    val url: String
)

/** Origen del personaje. */
data class Origin(
    val name: String,
    val url: String
)