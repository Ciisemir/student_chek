package com.example.student_chek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

data class Batch(
    val name: String,
    val location: String,
    val studentCount: Int,
    val leaderName: String,
    val leaderMobile: String
)
data class Student(
    val name: String,
    val domain: String,
    val mobile: String,
    val gender: String,
    val email: String
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val batchViewModel: BatchViewModel = viewModel()

            NavHost(navController = navController, startDestination = "splash") {

                composable("splash") { SplashScreen(navController) }
                composable("login") { LoginScreen(navController) }

                composable("home") { HomeScreen(navController, batchViewModel) }
                composable("add_batch") { AddBatchScreen(navController, batchViewModel) }
                composable("edit_batch/{batchIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    EditBatchScreen(navController, batchIndex, batchViewModel)
                }

                composable("batch_details/{index}") { backStackEntry ->
                    val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: -1
                    BatchDetailsScreen(navController, index, batchViewModel)
                }

                composable("student_list/{batchIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    StudentListScreen(navController, batchIndex, batchViewModel)
                }

                // **One single AddStudent route**
                composable("add_student/{batchIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    AddStudentScreen(navController, batchIndex, batchViewModel)
                }

                composable("student_details/{batchIndex}/{studentIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    val studentIndex = backStackEntry.arguments?.getString("studentIndex")?.toIntOrNull() ?: -1
                    StudentDetailsScreen(navController, batchIndex, studentIndex, batchViewModel)
                }

                composable("edit_student/{batchIndex}/{studentIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    val studentIndex = backStackEntry.arguments?.getString("studentIndex")?.toIntOrNull() ?: -1
                    EditStudentScreen(navController, batchIndex, studentIndex, batchViewModel)
                }

                composable("attendance/{batchIndex}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    AttendanceScreen(navController, batchIndex, batchViewModel)
                }

                composable("attendance_summary_graph/{batchIndex}/{date}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    val date = backStackEntry.arguments?.getString("date") ?: ""
                    AttendanceSummaryGraph(navController, batchIndex, date, batchViewModel)
                }

                composable("attendance_list_present/{batchIndex}/{date}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    val date = backStackEntry.arguments?.getString("date") ?: ""
                    AttendanceListScreen(navController, batchIndex, date, showPresent = true, batchViewModel)
                }

                composable("attendance_list_absent/{batchIndex}/{date}") { backStackEntry ->
                    val batchIndex = backStackEntry.arguments?.getString("batchIndex")?.toIntOrNull() ?: -1
                    val date = backStackEntry.arguments?.getString("date") ?: ""
                    AttendanceListScreen(navController, batchIndex, date, showPresent = false, batchViewModel)
                }
            }
        }
    }
}


