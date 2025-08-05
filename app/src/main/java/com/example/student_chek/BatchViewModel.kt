package com.example.student_chek

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class BatchViewModel : ViewModel() {

    // List of batches
    private val _batches = mutableStateListOf<Batch>()
    val batches: SnapshotStateList<Batch> get() = _batches

    // Key: Batch index, Value: List of students
    private val _studentsMap = mutableStateMapOf<Int, SnapshotStateList<Student>>()

    // Attendance data: Key = Pair(batchIndex, date), Value = Map<studentIndex, isPresent>
    private val attendanceData = mutableStateMapOf<Pair<Int, String>, MutableMap<Int, Boolean>>()

    /** Add a new batch */
    fun addBatch(batch: Batch) {
        _batches.add(batch)
    }

    /** Update an existing batch */
    fun updateBatch(index: Int, updatedBatch: Batch) {
        if (index in _batches.indices) {
            _batches[index] = updatedBatch
        }
    }

    /** Delete a batch and its students */
    fun deleteBatch(index: Int) {
        if (index in _batches.indices) {
            _batches.removeAt(index)
            _studentsMap.remove(index)

            // Re-index students map after deletion
            val updatedMap = mutableStateMapOf<Int, SnapshotStateList<Student>>()
            _studentsMap.forEach { (oldIndex, studentList) ->
                val newIndex = if (oldIndex > index) oldIndex - 1 else oldIndex
                updatedMap[newIndex] = studentList
            }
            _studentsMap.clear()
            _studentsMap.putAll(updatedMap)
        }
    }

    /** Get list of students in a batch */
    fun getStudents(batchIndex: Int): List<Student> {
        return _studentsMap[batchIndex]?.toList() ?: emptyList()
    }

    /** Add a student to a batch */
    fun addStudent(batchIndex: Int, student: Student) {
        val studentList = _studentsMap.getOrPut(batchIndex) { mutableStateListOf() }
        studentList.add(student)
    }

    /** Update a student's data */
    fun updateStudent(batchIndex: Int, studentIndex: Int, updatedStudent: Student) {
        _studentsMap[batchIndex]?.let { students ->
            if (studentIndex in students.indices) {
                students[studentIndex] = updatedStudent
            }
        }
    }

    /** Delete a student */
    fun deleteStudent(batchIndex: Int, studentIndex: Int) {
        _studentsMap[batchIndex]?.let { students ->
            if (studentIndex in students.indices) {
                students.removeAt(studentIndex)
            }
        }
    }

    /** Mark a student's attendance */
    fun markAttendance(batchIndex: Int, date: String, studentIndex: Int, isPresent: Boolean) {
        attendanceData.getOrPut(batchIndex to date) { mutableMapOf() }[studentIndex] = isPresent
    }

    /** Get summary of attendance for a batch on a specific date */
    fun getAttendanceSummary(batchIndex: Int, date: String): Pair<Int, Int> {
        val record = attendanceData[batchIndex to date] ?: return 0 to 0
        val present = record.values.count { it }
        val absent = record.values.count { !it }
        return present to absent
    }

    /** Get list of students who are present or absent */
    fun getAttendanceList(batchIndex: Int, date: String, present: Boolean): List<Student> {
        return attendanceData[batchIndex to date]
            ?.filter { it.value == present }
            ?.mapNotNull { (studentIndex, _) -> getStudents(batchIndex).getOrNull(studentIndex) }
            ?: emptyList()
    }

    /** Clear attendance for a batch on a specific date */
    fun clearAttendance(batchIndex: Int, date: String) {
        attendanceData.remove(batchIndex to date)
    }

    /** Export attendance as CSV */
    fun exportAttendanceToCSV(batchIndex: Int, date: String): String {
        val students = getStudents(batchIndex)
        val record = attendanceData[batchIndex to date] ?: emptyMap()
        val csv = StringBuilder().apply {
            append("Index,Name,Present\n")
            students.forEachIndexed { idx, student ->
                val present = record[idx] ?: false
                append("${idx + 1},\"${student.name}\",${if (present) "P" else "A"}\n")
            }
        }
        return csv.toString()
    }
}
