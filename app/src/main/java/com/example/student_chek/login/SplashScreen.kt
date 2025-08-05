package com.example.student_chek

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        // Sawirka kore: "Have a nice day..."
        Image(
            painter = painterResource(id = R.drawable.images),
            contentDescription = "Nice Day Text Image",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 32.dp)
                .size(width = 800.dp, height = 200.dp) // you can adjust these
        )

        // Sawirka hoose: attendance
        Image(
            painter = painterResource(id = R.drawable.attendance),
            contentDescription = "Attendance Image",
            modifier = Modifier
                .align(Alignment.BottomEnd)

                .size(width = 900.dp, height = 416.dp)
                .fillMaxWidth(0.85f)
        )
    }
}
