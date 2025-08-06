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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource

import java.util.*

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

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.kor),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Attendance image bottom-right
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
                .padding(top = 85.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            // Date
            Text(date, fontWeight = FontWeight.Bold, fontSize = 18.sp)

            // Attendance title
            Text("Attendance", fontWeight = FontWeight.Bold, fontSize = 22.sp)

            // Batch name
            Text(
                batchViewModel.batches.getOrNull(batchIndex)?.name ?: "N/A",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            // Pie chart
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

            // Percentage text
            Text(
                text = String.format("%.0f %%", presentPercent),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Legend
            Row(
                modifier = Modifier.padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                LegendItem(color = Color(0xFF66BB6A), label = "Present")
                LegendItem(color = Color(0xFFE57373), label = "Absent")
            }

            Spacer(modifier = Modifier.height(24.dp))
// Common padding modifier
            val buttonPadding = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)

// Wrapper for both buttons
            Column(
                modifier = Modifier.padding(top = 16.dp) // Optional spacing above the buttons
            ) {
                // Present students button
                Button(
                    onClick = {
                        navController.navigate("attendance_list_present/$batchIndex/$date")
                    },
                    modifier = buttonPadding,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF049696))
                ) {
                    Text("Present students", color = Color.White)
                }

                Spacer(modifier = Modifier.height(12.dp)) // Space between buttons

                // Absentees button
                Button(
                    onClick = {
                        navController.navigate("attendance_list_absent/$batchIndex/$date")
                    },
                    modifier = buttonPadding,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF049696))
                ) {
                    Text("Absentees", color = Color.White)
                }
            }
        }
    }
    }

