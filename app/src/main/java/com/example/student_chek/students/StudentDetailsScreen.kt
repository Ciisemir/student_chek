package com.example.student_chek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDetailsScreen(
    navController: NavHostController,
    batchIndex: Int,
    studentIndex: Int,
    batchViewModel: BatchViewModel
) {
    val student = batchViewModel.getStudents(batchIndex).getOrNull(studentIndex)
    val batchName = batchViewModel.batches.getOrNull(batchIndex)?.name ?: "N/A"
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (student == null) {
        Text("Student not found")
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background (optional)
        Image(
            painter = painterResource(id = R.drawable.kor),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Bottom-right image
        Image(
            painter = painterResource(id = R.drawable.attendance),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Edit + Delete buttons (top right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    navController.navigate("edit_student/$batchIndex/$studentIndex")
                }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF1BB6B6))
                }
                IconButton(onClick = {
                    showDeleteDialog = true
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFF1BB6B6))
                }
            }

            // Title
            Text(
                text = "Student Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )

            // Details list
            DetailItem("Batch name", batchName)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Name", student.name)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("facult", student.domain)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Mobile", student.mobile)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Email id", student.email)
            Spacer(modifier = Modifier.height(16.dp))
            DetailItem("Gender", student.gender)
        }

        // Delete confirmation dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Alert!", color = Color.Red) },
                text = { Text("Do you want to delete this student?") },
                confirmButton = {
                    TextButton(onClick = {
                        batchViewModel.deleteStudent(batchIndex, studentIndex)
                        navController.popBackStack()
                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$label : ",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

