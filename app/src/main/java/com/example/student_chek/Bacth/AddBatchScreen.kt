package com.example.student_chek

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun AddBatchScreen(
    navController: NavHostController,
    batchViewModel: BatchViewModel = viewModel()
) {
    var batchName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var studentCount by remember { mutableStateOf("") }
    var leaderName by remember { mutableStateOf("") }
    var leaderMobile by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.kor),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Form content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 120.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Enter Batch Details", fontSize = 20.sp)

            StyledOutlinedTextField(
                value = batchName,
                onValueChange = { batchName = it },
                label = "Enter batch name"
            )

            StyledOutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = "Enter location"
            )

            StyledOutlinedTextField(
                value = studentCount,
                onValueChange = { studentCount = it },
                label = "Enter number of students",
                keyboardType = KeyboardType.Number
            )

            Text("Batch Lead Details", fontSize = 18.sp)

            StyledOutlinedTextField(
                value = leaderName,
                onValueChange = { leaderName = it },
                label = "Enter batch leader's name"
            )

            StyledOutlinedTextField(
                value = leaderMobile,
                onValueChange = { leaderMobile = it },
                label = "Enter batch leader's mobile",
                keyboardType = KeyboardType.Phone
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val batch = Batch(
                        name = batchName,
                        location = location,
                        studentCount = studentCount.toIntOrNull() ?: 0,
                        leaderName = leaderName,
                        leaderMobile = leaderMobile
                    )
                    batchViewModel.addBatch(batch)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB6B6))
            ) {
                Text("Save")
            }
        }

        // Bottom-end image
        Image(
            painter = painterResource(id = R.drawable.attendance),
            contentDescription = "Bottom Image",
            modifier = Modifier
                .align(Alignment.BottomEnd)

                .size(110.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(50))
            .clip(RoundedCornerShape(50)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray,
            cursorColor = Color.Black
        )
    )
}
