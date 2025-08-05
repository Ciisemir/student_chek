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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    navController: NavHostController,
    batchIndex: Int,
    batchViewModel: BatchViewModel
) {
    val students = batchViewModel.getStudents(batchIndex)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Students Details") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_student/$batchIndex") },
                containerColor = Color(0xFF00BFA6)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Student", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            students.forEachIndexed { index, student ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("student_details/$batchIndex/$index")
                        },
                    shape = RoundedCornerShape(20.dp),
                    color = Color.LightGray.copy(alpha = 0.3f)
                ) {
                    Text(
                        text = student.name,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            if (students.isEmpty()) {
                Text("No students yet", modifier = Modifier.padding(24.dp))
            }
        }
    }
}
