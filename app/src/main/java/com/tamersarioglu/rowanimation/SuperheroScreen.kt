package com.tamersarioglu.rowanimation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SuperheroScreen(viewModel: SuperheroViewModel = viewModel()) {
    val heroes by viewModel.heroes.collectAsState()
    val startAnimation = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Superhero Power Levels", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(16.dp))

        heroes.forEach { hero ->
            SuperheroPowerRow(hero = hero, startAnimation = startAnimation.value)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { startAnimation.value = !startAnimation.value },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = if (startAnimation.value) "Reset" else "Show Power Levels")
        }
    }
}

@Composable
fun SuperheroPowerRow(hero: Superhero, startAnimation: Boolean) {
    Column {
        Text(text = hero.name, style = MaterialTheme.typography.bodyMedium)

        hero.powers.forEach { (powerName, powerLevel) ->
            PowerBar(powerName = powerName, powerLevel = powerLevel, startAnimation = startAnimation)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PowerBar(powerName: String, powerLevel: Int, startAnimation: Boolean) {
    val animatedPowerLevel = remember { Animatable(0f) }

    LaunchedEffect(startAnimation) {
        if (startAnimation) {
            animatedPowerLevel.animateTo(
                targetValue = powerLevel / 100f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        } else {
            animatedPowerLevel.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Column {
        Text(text = powerName, style = MaterialTheme.typography.bodyMedium)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray.copy(alpha = 0.3f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedPowerLevel.value) // Use the animated value
                    .background(PowerColors[powerName] ?: Color.Black)
            )
        }
    }
}

