@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pokedex.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.R
import com.example.pokedex.ui.screens.HomeScreen
import com.example.pokedex.PokemonViewModel
import com.example.pokedex.models.Pokemon
import com.example.pokedex.ui.screens.PokemonDetails

enum class PokemonScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    PokemonDetails(title = R.string.pokemon_details)
}

@Composable
fun PokemonApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PokemonScreen.valueOf(
        backStackEntry?.destination?.route ?: PokemonScreen.Start.name
    )

    var currentPokemon: Pokemon? by remember { mutableStateOf(null) }
    Scaffold(
        topBar = { PokemonTopAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        ) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val pokemonViewModel: com.example.pokedex.PokemonViewModel =
                viewModel(factory = com.example.pokedex.PokemonViewModel.Factory)
            NavHost(
                navController = navController,
                startDestination = PokemonScreen.Start.name,
//          modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = PokemonScreen.Start.name) {
                    HomeScreen(
                        pokemonUiState = pokemonViewModel.pokemonUiState,
                        retryAction = pokemonViewModel::getPokemon,
                        onPokemonClicked = {
                            currentPokemon = it
                            navController.navigate(PokemonScreen.PokemonDetails.name)
                        },
                    )
                }
                composable(route = PokemonScreen.PokemonDetails.name) {
                    PokemonDetails(
                        pokemon = currentPokemon!!,
                        modifier = Modifier
                            .fillMaxSize()
//                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonTopAppBar(
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier
    ) {
    TopAppBar(
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Vide"
                    )
                }
            }
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
                modifier = modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                textAlign = TextAlign.Center
            )
        },
    )
}

