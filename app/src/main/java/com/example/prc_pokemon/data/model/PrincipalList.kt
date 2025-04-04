package com.example.prc_pokemon.data.model

import com.google.gson.annotations.SerializedName

/*
Para la lista de opciones a utilizar.
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
