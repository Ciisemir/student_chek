package com.example.student_chek

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.student_chek.BatchViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.Search

import androidx.compose.material3.*


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    navController: NavHostController,
    batchIndex: Int,
    batchViewModel: BatchViewModel
) {
    val students = batchViewModel.getStudents(batchIndex)
    var searchQuery by remember { mutableStateOf("") }

    val filteredStudents = students.filter {
        it.name.contains(searchQuery, ignoreCase = true)
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
            painter = painterResource(id = R.drawable.attendance), // attendance image
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(100.dp)

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Search Field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search..") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 30.dp)
                    .clip(RoundedCornerShape(1))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Title
            Text(
                text = "Students Details",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // List of students
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredStudents.size) { index ->
                    val student = filteredStudents[index]
                    Button(
                        onClick = {
                            val realIndex = students.indexOf(student)
                            navController.navigate("student_details/$batchIndex/$realIndex")
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF2F2F2),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(student.name)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Add New Student Button
            Button(
                onClick = { navController.navigate("add_student/$batchIndex") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1BB6B6),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 100.dp)

            ) {
                Text("Add New Student")
            }
        }
    }
}
