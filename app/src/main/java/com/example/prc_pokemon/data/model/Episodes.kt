package com.example.prc_pokemon.data.model

/** Clase de datos para los episodios
 * @param episodeInfo Paginacion de episodios.
 * @param results Lista de cada uno de los episodios con su informacion*/
data class Episodes(
    val episodeInfo: EpisodeInfo,
    val results: List<SingleEpisode>
)

/** Para paginacion de episodios.*/
data class EpisodeInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

/** Informacion de cada uno de los episodios..*/
data class SingleEpisode(
    val air_date: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)