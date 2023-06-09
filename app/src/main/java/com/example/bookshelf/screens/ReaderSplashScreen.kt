package com.example.bookshelf.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookshelf.components.ReaderLogo
import com.example.bookshelf.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Preview
@Composable
fun ReaderSplashScreen(navController: NavController  = NavController(LocalContext.current)) {
    val scale = remember{
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            0.9f,
            animationSpec = tween(
                durationMillis = 800,
                easing = { OvershootInterpolator(8f).getInterpolation(it) })
        )
        delay(2000L)
//        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
//        {navController.navigate(ReaderScreens.ReaderLoginScreen.name)}
//        else
//        {navController.navigate(ReaderScreens.ReaderHomeScreen.name)}
        navController.navigate(ReaderScreens.ReaderLoginScreen.name)
    }
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp).scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(1.dp) , horizontalAlignment = Alignment.CenterHorizontally , verticalArrangement = Arrangement.Center) {
            ReaderLogo()
            Text(text = "Read to grow" , style = MaterialTheme.typography.caption , color = Color.Black)

        }

    }
}

