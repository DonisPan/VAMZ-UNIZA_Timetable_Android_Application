package project.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import project.LectureType
import project.Subjects
import project.timetable.TimeTable

@Composable
fun EditorScreen(navController: NavController, index: Int, onSave: (TimeTable.ClassBlock) -> Unit) {
    val viewModel: EditorViewModel = viewModel()

    Editor(
        subject = viewModel.subject,
        onSubjectChange = { viewModel.updateSubject(it) },
        teacherName = viewModel.teacherName,
        onTeacherChange = { viewModel.updateTeacher(it) },
        type = viewModel.type,
        onTypeChange = { viewModel.updateType(it) },
        classRoom = viewModel.classRoom,
        onClassRoomChange = { viewModel.updateClassRoom(it) },
        onSave = {
            onSave(viewModel.toClassBlock())
            navController.popBackStack()
        }
    )
}

@Composable
fun Editor(
    subject: Subjects,
    onSubjectChange: (Subjects) -> Unit,
    teacherName: String,
    onTeacherChange: (String) -> Unit,
    type: LectureType?,
    onTypeChange: (LectureType?) -> Unit,
    classRoom: String,
    onClassRoomChange: (String) -> Unit,
    onSave: () -> Unit
) {
    var expandSubjects by remember { mutableStateOf(false) }
    var expandTeachers by remember { mutableStateOf(false) }
    var expandTypes by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text("Nova Hodina", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }
        // SUBJECTS
        item {
            Box {
                Text(
                    text = subject.getName(),
                    modifier = Modifier
                        .clickable { expandSubjects = true }
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expandSubjects,
                    onDismissRequest = { expandSubjects = false }
                ) {
                    Subjects.entries.forEach { subjectItem ->
                        DropdownMenuItem(
                            text = { Text(subjectItem.getName()) },
                            onClick = {
                                onSubjectChange(subjectItem)
                                onTeacherChange(subjectItem.teachers.first())
                                expandSubjects = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        // TEACHERS
        item {
            Box {
                Text(
                    text = teacherName,
                    modifier = Modifier
                        .clickable { expandTeachers = true }
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expandTeachers,
                    onDismissRequest = { expandTeachers = false }
                ) {
                    subject.teachers.forEach { teacher ->
                        DropdownMenuItem(
                            text = { Text(teacher) },
                            onClick = {
                                onTeacherChange(teacher)
                                expandTeachers = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        // CLASSROOM NUMBER
        item {
            TextField(
                value = classRoom,
                onValueChange = onClassRoomChange,
                label = { Text("Číslo miestnosti") },
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        // TYPE OF LECTURE
        item {
            Box {
                Text(
                    text = type?.name ?: "",
                    modifier = Modifier
                        .clickable { expandTypes = true }
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontSize = 16.sp
                )
                DropdownMenu(
                    expanded = expandTypes,
                    onDismissRequest = { expandTypes = false }
                ) {
                    LectureType.entries.forEach { typeItem ->
                        DropdownMenuItem(
                            text = { Text(typeItem.name) },
                            onClick = {
                                onTypeChange(typeItem)
                                expandTypes = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        //SAVE BUTTON
        item {
            Button(onClick = onSave,
                modifier = Modifier.fillMaxWidth())
            {
                Text("Ulož")
            }
        }
    }
}

