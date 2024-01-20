package com.example.pokedex

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeScreenInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_pokemonList_isDisplayed() {
        onView(withId(R.id.pokemon_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectPokemon_navigatesToDetailScreen() {
        // Assurez-vous qu'un élément spécifique de la liste est cliqué
        onView(withId(R.id.pokemon_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.ViewHolder>(0, click()))

        // Vérifiez que l'écran de détail est affiché
        onView(withId(R.id.pokemon_detail)).check(matches(isDisplayed()))
    }
}
