package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.model.Urls
import retrofit2.http.GET
import retrofit2.http.Url


//Interfaz retrofit para definir peticiones.
interface ApiService {

    /**Devuelve tres links: Characters, Locations, Epidoses.*/
    @GET("api")
    suspend fun getRickMortyListData(): Urls

    /**Devuelve lista de los primeros 20 personajes de la serie.*/
    @GET("api/character")
    suspend fun getCharactersList(): Characters

    /**Devuelve lista de 20 personajes de la siguiente pagina.*/
    @GET
    suspend fun getCharactersNextPage(@Url url: String): Characters

    /**Devuelve lista de 20 personajes de la anterior pagina.*/
    @GET
    suspend fun getCharactersPreviousPage(@Url url: String): Characters

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