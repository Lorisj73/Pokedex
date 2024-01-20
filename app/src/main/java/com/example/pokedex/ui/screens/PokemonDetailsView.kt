package com.example.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.pokedex.models.Pokemon

// VUE DES DETAILS DES POKEMONS
@Composable
fun PokemonDetailsScreen(
    pokemon: Pokemon,
    pokemonUiState: com.example.pokedex.PokemonUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (pokemonUiState) {
        is com.example.pokedex.PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is com.example.pokedex.PokemonUiState.Success -> PokemonDetails(
            pokemon,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )

        is com.example.pokedex.PokemonUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

// Rendu des details des Pokémons
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetails(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Box() {
            Column (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium)
            ){
                // Pokemon Name with bigger font size, centered, and red background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .align(Alignment.CenterHorizontally)
                        .clip(MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    Text(
                        text = pokemon.name,
                        style = MaterialTheme.typography.titleLarge, // Use h4 for bigger font size
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }

                // Pokemon Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.25f)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary)
                        .shadow(4.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                // Description (italic)
                Text(
                    text = pokemon.description,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .offset(y = 25.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                        .padding(30.dp)
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
        ){
            Column (
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Attributs",
                    style = MaterialTheme.typography.titleMedium, // Use h4 for bigger font size
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                )

                // Pokemon Types
                Text(
                    text = "Types : " +pokemon.type.joinToString(", "),
                    color = Color.Black,
                )

                // Pokemon Evolutions
                Text(
                    text = "Provient de : " + if (pokemon.evolutions.before.isEmpty()) "aucun pokemon" else pokemon.evolutions.before.toString(),
                    color = Color.Black,
                )
                Text(
                    text = "Évolue en : " + if (pokemon.evolutions.after.isEmpty()) "aucun pokemon" else pokemon.evolutions.after.toString(),
                    color = Color.Black,
                )
            }
        }
    }
}



