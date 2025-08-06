package com.example.student_chek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun EditBatchScreen(
    navController: NavHostController,
    batchIndex: Int,
    batchViewModel: BatchViewModel = viewModel()
) {
    val batch = batchViewModel.batches.getOrNull(batchIndex)

    if (batch == null) {
        Text("Batch not found")
        return
    }

    var batchName by remember { mutableStateOf(batch.name) }
    var location by remember { mutableStateOf(batch.location) }
    var studentCount by remember { mutableStateOf(batch.studentCount.toString()) }
    var leaderName by remember { mutableStateOf(batch.leaderName) }
    var leaderMobile by remember { mutableStateOf(batch.leaderMobile) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Edit Batch Details", fontSize = 20.sp)

        OutlinedTextField(
            value = batchName,
            onValueChange = { batchName = it },
            label = { Text("Enter batch name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Enter location") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = studentCount,
            onValueChange = { studentCount = it },
            label = { Text("Enter number of students") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Batch Lead Details", fontSize = 18.sp)

        OutlinedTextField(
            value = leaderName,
            onValueChange = { leaderName = it },
            label = { Text("Enter batch leader's name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = leaderMobile,
            onValueChange = { leaderMobile = it },
            label = { Text("Enter batch leader's mobile") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val updatedBatch = Batch(
                    name = batchName,
                    location = location,
                    studentCount = studentCount.toIntOrNull() ?: 0,
                    leaderName = leaderName,
                    leaderMobile = leaderMobile
                )
                batchViewModel.updateBatch(batchIndex, updatedBatch)
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB6B6))
        ) {
            Text("Save", color = Color.White)
        }
    }
}