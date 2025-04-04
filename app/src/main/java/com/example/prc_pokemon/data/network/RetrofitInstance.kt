package com.example.prc_pokemon.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Objeto singleton para configuracion retrofit
object RetrofitInstance {

    private const val BASE_URL = "https://rickandmortyapi.com/"

    val retrofitBuilder: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}