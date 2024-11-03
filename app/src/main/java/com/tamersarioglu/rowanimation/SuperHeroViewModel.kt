package com.tamersarioglu.rowanimation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SuperheroViewModel : ViewModel() {
    private val _heroes = MutableStateFlow(
        listOf(
            Superhero("Hero1", mapOf("Strength" to 80, "Speed" to 60, "Intelligence" to 90)),
            Superhero("Hero2", mapOf("Strength" to 50, "Speed" to 80, "Intelligence" to 70)),
            Superhero("Hero3", mapOf("Strength" to 70, "Speed" to 50, "Intelligence" to 60))
        )
    )
    val heroes: StateFlow<List<Superhero>> = _heroes
}