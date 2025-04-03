package com.example.prc_pokemon.data.network

import com.example.prc_pokemon.data.model.RickMortyModel
import retrofit2.http.GET


//Interfaz retrofit para definir peticiones.
interface ApiService{

    /*@GET("pokemon?limit=10&offset=0") //Endpoint.
    suspend fun getPokemonListData(): PokemonSearchModel*/

    @GET("api")
    suspend fun getRickMortyListData(): RickMortyModel

    @GET("character")
    suspend fun getCharacters()

    @GET("locations")
    suspend fun getLocations()

    @GET("episodes")
    suspend fun getEpisodes()

    @GET("")
    suspend fun demoData(

    )

}