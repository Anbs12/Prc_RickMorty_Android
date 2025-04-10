package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.model.Urls
import retrofit2.http.GET


//Interfaz retrofit para definir peticiones.
interface ApiService {

    /**Devuelve tres links: Characters, Locations, Epidoses.*/
    @GET("api")
    suspend fun getRickMortyListData(): Urls

    /**Devuelve lista de todos los personajes de la serie.*/
    @GET("api/character")
    suspend fun getCharactersList(): Characters

    //TODO crear paginacion o ampliacion de la lista para terminar app.
    @GET("api/character//?page=3")
    suspend fun getNextPage(): Characters

    /**Devuelve lista de todos las localizaciones de la serie.*/
    @GET("api/location")
    suspend fun getLocations(): Locations

    /**Devuelve lista de todos los episodios de la serie.*/
    @GET("api/episode")
    suspend fun getEpisodes(): Episodes

}