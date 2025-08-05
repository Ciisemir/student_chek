package com.example.student_chek

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentScreen(
    navController: NavHostController,
    batchIndex: Int,
    studentIndex: Int,
    batchViewModel: BatchViewModel = viewModel()
) {
    val student = batchViewModel.getStudents(batchIndex).getOrNull(studentIndex)
    if (student == null) {
        Text("Student not found")
        return
    }

    var name by remember { mutableStateOf(student.name) }
    var domain by remember { mutableStateOf(student.domain) }
    var mobile by remember { mutableStateOf(student.mobile) }
    var gender by remember { mutableStateOf(student.gender) }
    var email by remember { mutableStateOf(student.email) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Student Details") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Batch name: ${batchViewModel.batches.getOrNull(batchIndex)?.name ?: "Unknown"}")

            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("Mobile") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val updated = Student(name, domain, mobile, gender, email)
                    batchViewModel.updateStudent(batchIndex, studentIndex, updated)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFA6))
            ) {
                Text("Save", color = Color.White)
            }
        }
    }
}