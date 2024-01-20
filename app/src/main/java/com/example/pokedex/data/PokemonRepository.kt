package com.example.pokedex.data

import com.example.pokedex.models.Pokemon
import com.example.pokedex.services.PokemonApiService


interface PokemonRepository {

    suspend fun getPokemons(): List<Pokemon>
}


class NetworkPokemonRepository(
    private val pokemonService: PokemonApiService
) : PokemonRepository {
    override suspend fun getPokemons(): List<Pokemon> = pokemonService.getPokemons()
}
