package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
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

    @GET("locations")
    suspend fun getLocations()

    @GET("episodes")
    suspend fun getEpisodes()



    @GET("")
    suspend fun demoData(

    )

}