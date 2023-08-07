package com.example.login_cba.pages.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.example.login_cba.R
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun SplashScreen() {
    val isVisible = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2000)
        isVisible.value = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val rotationState = remember { Animatable(initialValue = 0f) }

        LaunchedEffect(isVisible.value) {
            if (isVisible.value) {
                rotationState.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 2000)
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.ic_logo_cba),
            contentDescription = "Splash Screen Image",
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    rotationZ = if (isVisible.value) {
                        rotationState.value * 360f
                    } else {
                        0f
                    }
                }
        )

        AnimatedVisibility(
            visible = isVisible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
            )
        }
    }
}
