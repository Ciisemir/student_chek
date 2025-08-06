package com.example.student_chek

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceListScreen(
    navController: NavHostController,
    batchIndex: Int,
    date: String,
    showPresent: Boolean,
    batchViewModel: BatchViewModel = viewModel()
) {
    val allStudents = batchViewModel.getAttendanceList(batchIndex, date, present = showPresent)
    var searchQuery by remember { mutableStateOf("") }

    val filteredStudents = allStudents.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    val title = if (showPresent) "Present Students" else "Absentees"
    val backgroundColor = if (showPresent) Color(0xFFD0F8CE) else Color(0xFFFFCDD2)

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.kor),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Attendance icon bottom-right
        Image(
            painter = painterResource(id = R.drawable.attendance),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Back button
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }

            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 100.dp),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.LightGray
                )
            )

            // Title
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Date
            Text(date, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)

            // Student list
            if (filteredStudents.isNotEmpty()) {
                filteredStudents.forEachIndexed { index, student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor, shape = RoundedCornerShape(30.dp))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${(index + 1).toString().padStart(2, '0')}   ${student.name}")
                        Text(if (showPresent) "P" else "A", fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    "No students found",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
