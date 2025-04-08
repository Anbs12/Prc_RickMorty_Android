package com.example.prc_pokemon.data.model

import com.google.gson.annotations.SerializedName

/*
Esta es la clase de datos que representa la primera lista donde el  usuario selecciona
si va a personajes, episodios o localizaciones.
 */

data class PrincipalList(
    val name: String = "",
    val url: String = ""
)

data class Urls(

    @SerializedName(value = "character")
    val personajes: String = "",

    val episodes: String = "",
    val locations: String = ""
)
