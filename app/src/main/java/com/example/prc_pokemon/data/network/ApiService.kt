package com.example.prc_pokemon.data.network

import retrofit2.http.GET


//Interfaz retrofit para definir peticiones.
interface ApiService{

    @GET("pokemon/") //Endpoint.
    suspend fun getData(): String

}