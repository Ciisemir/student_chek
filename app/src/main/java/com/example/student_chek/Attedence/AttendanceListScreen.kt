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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceListScreen(
    navController: NavHostController,
    batchIndex: Int,
    date: String,
    showPresent: Boolean,
    batchViewModel: BatchViewModel = viewModel()
) {
    val students = batchViewModel.getAttendanceList(batchIndex, date, present = showPresent)

    val title = if (showPresent) "Present Students" else "Absentees"
    val backgroundColor = if (showPresent) Color(0xFFD0F8CE) else Color(0xFFFFCDD2)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(date, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

            students.forEachIndexed { index, student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${(index + 1).toString().padStart(2, '0')}. ${student.name}")
                    Text(if (showPresent) "P" else "A", fontWeight = FontWeight.Bold)
                }
            }

            if (students.isEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text("No students found", color = Color.Gray, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}
