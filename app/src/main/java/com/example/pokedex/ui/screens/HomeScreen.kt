package com.example.pokedex.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedex.R
import com.example.pokedex.models.Pokemon
import com.example.pokedex.ui.theme.PokemonTheme

@Composable
fun HomeScreen(
    pokemonUiState: com.example.pokedex.PokemonUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onPokemonClicked: (Pokemon) -> Unit,
) {
    when (pokemonUiState) {
        is com.example.pokedex.PokemonUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is com.example.pokedex.PokemonUiState.Success -> PhotosGridScreen(
            pokemonUiState.photos,
            modifier = Modifier
                .height(100.dp)
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
                .fillMaxHeight(),
            onPokemonClicked = onPokemonClicked
        )
        is com.example.pokedex.PokemonUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PhotosGridScreen(
    pokemons: List<Pokemon>,
    modifier: Modifier = Modifier,
    onPokemonClicked: (Pokemon) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),  // Ajustez la largeur des colonnes ici
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(175.dp)
    ) {
        items(items = pokemons, key = { pokemon -> pokemon.id }) { pokemon ->
            PokemonCard(
                pokemon,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.2f),  // Ajustez l'aspect ratio ici pour la hauteur des cartes
                onPokemonClicked = {
                    onPokemonClicked(it)
                }
            )
        }
    }
}

@Composable
fun backgroundColorForType(type: String): Color {
    // Assignez une couleur en fonction du type
    return when (type.toLowerCase()) {
        "feu" -> Color(android.graphics.Color.parseColor("#cc0000")) // Rouge
        "plante" -> Color(android.graphics.Color.parseColor("#6aa84f")) // Vert foncé
        "poison" -> Color(android.graphics.Color.parseColor("#a64d79")) // violet
        "eau" -> Color(android.graphics.Color.parseColor("#6fa8dc")) // bleu
        "insecte" -> Color(android.graphics.Color.parseColor("#8fce00")) // vert clair
        "normal" -> Color(android.graphics.Color.parseColor("#bcbcbc")) // gris
        else -> Color.Gray
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onPokemonClicked: (Pokemon) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(0.75f), // Ajustez l'aspect ratio pour définir la hauteur des cartes
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { onPokemonClicked(pokemon) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "${pokemon.id}: ${pokemon.name}",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(8.dp)
            )

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(pokemon.imageUrl)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.pokemons),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            val backgroundColor = backgroundColorForType(pokemon.type.firstOrNull() ?: "")
            Text(
                text = pokemon.type.joinToString(", "),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(backgroundColor)
                    .padding(8.dp)
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PokemonTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PokemonTheme {
        ErrorScreen({})
    }
}
