package com.example.pokedex

import android.app.Application
import com.example.pokedex.data.AppContainer
import com.example.pokedex.data.DefaultAppContainer

class PokemonApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
