package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.model.Urls
import retrofit2.http.GET


//Interfaz retrofit para definir peticiones.
interface ApiService{

    //Devuelve tres links: Characters, Locations, Epidoses.
    @GET("api")
    suspend fun getRickMortyListData(): Urls

    //Devuelve lista de todos los personajes.
    @GET("api/character")
    suspend fun getCharactersList() : Characters

    //Devuelve un personaje, ingresando ID seleccionado por el usuario.
    @GET("")
    suspend fun getCharacter()

    @GET("api/location")
    suspend fun getLocations() : Locations

    @GET("api/episode")
    suspend fun getEpisodes() : Episodes



    @GET("")
    suspend fun demoData(

    )

}