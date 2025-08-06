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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    navController: NavHostController,
    batchIndex: Int,
    batchViewModel: BatchViewModel = viewModel()
) {
    val students = batchViewModel.getStudents(batchIndex)
    val date = remember { SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(Date()) }

    var attendanceState by remember {
        mutableStateOf(
            students.indices.associateWith { true }.toMutableMap()
        )
    }

    var searchQuery by remember { mutableStateOf("") }
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
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true,
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                }
            )

            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Filter students based on search query
            val filteredStudents = if (searchQuery.isEmpty()) {
                students
            } else {
                students.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                itemsIndexed(filteredStudents) { index, student ->
                    val originalIndex = students.indexOf(student)
                    val isPresent = attendanceState[originalIndex] ?: true

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isPresent) Color(0xFFD0F8CE) else Color(0xFFFFCDD2),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(vertical = 12.dp, horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${(originalIndex + 1).toString().padStart(2, '0')}")
                        Text(student.name, modifier = Modifier.weight(1f).padding(start = 16.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("P", fontWeight = FontWeight.Bold)
                            Switch(
                                checked = isPresent,
                                onCheckedChange = { checked ->
                                    attendanceState = attendanceState.toMutableMap().also {
                                        it[originalIndex] = checked
                                    }
                                }
                            )
                            Text("A", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
// Spacer kor loogu qaadayo
            Spacer(modifier = Modifier.height(24.dp))

// Column ku jira labada button oo wadaaga padding
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( start = 35.dp, end = 35.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp) // kala wareegto labada button
            ) {
                Button(
                    onClick = {
                        attendanceState.forEach { (studentIndex, isPresent) ->
                            batchViewModel.markAttendance(batchIndex, date, studentIndex, isPresent)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB6B6))
                ) {
                    Text("Save", color = Color.White)
                }

                Button(
                    onClick = {
                        navController.navigate("attendance_summary_graph/$batchIndex/$date")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                    .height(50.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1BB6B6))
                ) {
                    Text("View Attendance", color = Color.White)
                }
            }
        }
    }
}
