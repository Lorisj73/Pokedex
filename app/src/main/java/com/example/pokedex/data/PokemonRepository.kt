package com.example.pokedex.data

import com.example.pokedex.models.Pokemon
import com.example.pokedex.services.PokemonApiService

/**
 * Repository that fetch pokemon list from pokemonApi.
 */
interface PokemonRepository {
    /** Fetches list of PokemonPhoto from pokemonApi */
    suspend fun getPokemons(): List<Pokemon>
}

/**
 * Network Implementation of Repository that fetch pokemon list from pokemonApi.
 */
class NetworkPokemonRepository(
    private val pokemonService: PokemonApiService
) : PokemonRepository {
    /** Fetches list of Pokemon from marsApi*/
    override suspend fun getPokemons(): List<Pokemon> = pokemonService.getPokemons()
}
