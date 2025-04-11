package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.model.Urls
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


//Interfaz retrofit para definir peticiones.
interface ApiService {

    /**Devuelve tres links: Characters, Locations, Epidoses.*/
    @GET("api")
    suspend fun getRickMortyListData(): Urls

    /**Devuelve lista de todos los personajes de la serie.*/
    @GET("api/character")
    suspend fun getCharactersList(): Characters

    @GET("api/character/?page=")
    suspend fun getCharactersNextPage(@Query(value = "page") page: Int): Characters

    //TODO continuar aplicando y añadiendo paginacion a la pantalla personajes.
    @GET("api/character/?page=")
    suspend fun getCharactersPreviousPage(@Query(value = "page") page: Int): Characters

    /**Devuelve lista de todos las localizaciones de la serie.*/
    @GET("api/location")
    suspend fun getLocations(): Locations

    /**Devuelve lista de todos los episodios de la serie.*/
    @GET("api/episode")
    suspend fun getEpisodes(): Episodes


    //EJEMPLO CON PATH
    //@GET("{data}")
    //suspend fun demo(@Path("data") data: Int): Characters
}