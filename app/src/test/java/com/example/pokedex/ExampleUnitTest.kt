package com.example.pokedex

import com.example.pokedex.data.PokemonRepository
import com.example.pokedex.models.Evolutions
import com.example.pokedex.models.Pokemon
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TestPokemonRepository : PokemonRepository {
    override suspend fun getPokemons(): List<Pokemon> {
        // Retournez ici une liste de Pokémon prédéfinis
        return listOf(
            Pokemon(1, "Bulbizarre", listOf("Plante", "Poison"), "Description de Bulbasaur", "url", Evolutions(emptyList(), listOf(2))),
            // Ajoutez d'autres Pokémon si nécessaire
        )
    }
}