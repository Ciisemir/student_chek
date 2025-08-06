package com.example.student_chek

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
@Composable
fun BatchDetailsScreen(
    navController: NavHostController,
    batchIndex: Int,
    batchViewModel: BatchViewModel = viewModel()
) {
    val batch = batchViewModel.batches.getOrNull(batchIndex)

    if (batch == null) {
        Text("Batch not found")
        return
    }

    var showDeleteDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ Background image
        Image(
            painter = painterResource(id = R.drawable.kor), // ← USE your background here
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    navController.navigate("edit_batch/$batchIndex")
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF1BB6B6)
                    )
                }

                IconButton(onClick = {
                    showDeleteDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }

            Text(
                text = "Batch Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            InfoRow(label = "Batch name", value = batch.name)
            Spacer(modifier = Modifier.height(35.dp))
            InfoRow(label = "Location", value = batch.location)
            Spacer(modifier = Modifier.height(35.dp))
            InfoRow(label = "Number of students", value = batch.studentCount.toString())

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Batch Lead Details",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(50.dp))

            InfoRow(label = "Batch Leader", value = batch.leaderName)
            Spacer(modifier = Modifier.height(35.dp))
            InfoRow(label = "Mobile", value = batch.leaderMobile)

            Spacer(modifier = Modifier.height(50.dp))

            RoundedButton(
                text = "Attendance",
                onClick = { navController.navigate("attendance/$batchIndex") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoundedButton(
                text = "Student details",
                onClick = { navController.navigate("student_list/$batchIndex") }
            )
        }

        // Bottom image
        Image(
            painter = painterResource(id = R.drawable.attendance),
            contentDescription = "Bottom Image",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(110.dp),
            contentScale = ContentScale.Fit
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        batchViewModel.deleteBatch(batchIndex)
                        navController.popBackStack()
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Delete Batch") },
            text = { Text("Are you sure you want to delete this batch?") }
        )
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$label :", fontWeight = FontWeight.Normal)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun RoundedButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB6B6))
    ) {
        Text(text, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
