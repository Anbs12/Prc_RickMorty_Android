package com.example.prc_pokemon.data.repository

import com.example.prc_pokemon.data.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Repositorio que interactua con la API. (INYECCION DE DEPENDENCIA PARECE)
class Repository(private val apiService: ApiService) {


    /*fun fetchData(): Flow<List<String>> = flow {
        emit(apiService.getData())
    }*/

}