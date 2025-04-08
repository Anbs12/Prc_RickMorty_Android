package com.example.prc_pokemon.data.model

import com.google.gson.annotations.SerializedName

/*
Esta es la clase de datos que representa la primera lista donde el  usuario selecciona
si va a personajes, episodios o localizaciones.
 */

/** Clade datos que representa los endpoints de la primera pantalla "MainScreen" */
data class PrincipalList(
    val name: String = "",
    val url: String = ""
)

/**Endpoints para dirigirse a los sitios indicados.*/
data class Urls(

    @SerializedName(value = "character")
    val personajes: String = "",

    val episodes: String = "",
    val locations: String = ""
)
