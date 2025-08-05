package com.example.student_chek

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
fun AttendanceSummaryGraph(
    navController: NavHostController,
    batchIndex: Int,
    date: String,
    batchViewModel: BatchViewModel = viewModel()
) {
    val (presentCount, absentCount) = batchViewModel.getAttendanceSummary(batchIndex, date)
    val total = presentCount + absentCount
    val presentPercent = if (total > 0) presentCount * 100f / total else 0f

    val pieColors = listOf(Color(0xFF66BB6A), Color(0xFFE57373)) // green & red
    val values = listOf(presentCount.toFloat(), absentCount.toFloat())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance") },
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
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(date, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Canvas(modifier = Modifier.size(200.dp)) {
                var startAngle = -90f
                val totalSum = values.sum()
                values.forEachIndexed { index, value ->
                    val sweepAngle = if (totalSum == 0f) 0f else 360f * (value / totalSum)
                    drawArc(
                        color = pieColors[index],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true
                    )
                    startAngle += sweepAngle
                }
            }

            Text(
                text = String.format("%.1f%% Present", presentPercent),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LegendItem(color = Color(0xFF66BB6A), label = "Present")
                LegendItem(color = Color(0xFFE57373), label = "Absent")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("attendance_list_present/$batchIndex/$date")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA6))
            ) {
                Text("Present students", color = Color.White)
            }

            Button(
                onClick = {
                    navController.navigate("attendance_list_absent/$batchIndex/$date")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA6))
            ) {
                Text("Absentees", color = Color.White)
            }
        }
    }
}
