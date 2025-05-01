package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.Characters
import com.example.prc_pokemon.data.model.Episodes
import com.example.prc_pokemon.data.model.Locations
import com.example.prc_pokemon.data.model.SingleEpisode
import com.example.prc_pokemon.data.model.Urls
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


/**Interfaz retrofit para definir peticiones y/o funciones.*/
interface ApiService {

    /*
    * * Main Screen
    * */

    /**Devuelve tres links: Characters, Locations, Epidoses.*/
    @GET("api")
    suspend fun getRickMortyListData(): Urls

    /**Devuelve lista de los primeros 20 personajes de la serie.*/
    @GET("api/character")
    suspend fun getCharactersList(): Characters

    /*
    * * Characters Screen
    * */

    /**Devuelve lista de 20 personajes de la siguiente pagina.*/
    @GET
    suspend fun getCharactersNextPage(@Url url: String): Characters

    /**Devuelve lista de 20 personajes de la anterior pagina.*/
    @GET
    suspend fun getCharactersPreviousPage(@Url url: String): Characters

    /**Devuelve los personajes ingresados por su nombre.
     * @param nombre Nombre del personaje que se desea encontrar.*/
    @GET(value = "api/character/")
    suspend fun getFilteredCharacter(@Query("name") nombre: String): Characters

    /*
    * * Locations Screen
    * */

    /**Devuelve lista de todos las localizaciones de la serie.*/
    @GET("api/location")
    suspend fun getLocations(): Locations

    @GET
    suspend fun getNextLocations(@Url url: String): Locations

    /*
    * * Episodes Screen
    * */

    /**Devuelve lista de todos los episodios de la serie.*/
    @GET("api/episode")
    suspend fun getEpisodes(): Episodes

    /**Devuelve informacion del Episodio seleccionado.*/
    @GET("api/episode/{id}")
    suspend fun getSingleEpisodeInfo(@Path ("id") id: Int): SingleEpisode

    //EJEMPLO CON PATH
    //@GET("{data}")
    //suspend fun demo(@Path("data") data: Int): Characters
}