package com.example.pokedex.data

import com.example.pokedex.data.NetworkPokemonRepository
import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.services.PokemonApiService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType


interface AppContainer {
    val pokemonRepository: PokemonRepository
}


class DefaultAppContainer : AppContainer {
    // Appel API
    private val baseUrl = "https://raw.githubusercontent.com/Josstoh/res508-qualite-dev-android/main/rest/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }

    override val pokemonRepository: PokemonRepository by lazy {
        NetworkPokemonRepository(retrofitService)
    }
}
