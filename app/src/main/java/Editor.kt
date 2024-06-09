import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Editor(index: Int, onSave: (TimeTable.ClassBlock) -> Unit) {
    var subject by remember { mutableStateOf(Subjects.entries[0]) }
    var expandSubjects by remember { mutableStateOf(false) }

    var teacherName by remember { mutableStateOf(subject.teachers.first()) }
    var expandTeachers by remember { mutableStateOf(false) }

    var type by remember { mutableStateOf<LectureType?>(LectureType.entries.first()) }
    var expandTypes by remember { mutableStateOf(false) }

    var classRoom by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("New Class", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        //SUBJECTS
        Box {
            Text(
                text = subject.getName(),
                modifier = Modifier
                    .clickable { expandSubjects = true }
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
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
                            subject = subjectItem
                            teacherName = subject.teachers.first()
                            expandSubjects = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //TEACHERS
        Box {
            Text(
                text = teacherName,
                modifier = Modifier
                    .clickable { expandTeachers = true }
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
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
                            teacherName = teacher
                            expandTeachers = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        //CLASSROOM NUMBER
        TextField(
            value = classRoom,
            onValueChange = { classRoom = it },
            label = { Text("Enter a classroom number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        //TYPE OF LECTURE
        Box {
            Text(
                text = type?.name ?: "",
                modifier = Modifier
                    .clickable { expandTypes = true }
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
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
                            type = typeItem
                            expandTypes = false
                        }
                    )
                }
            }
        }

        Button(onClick = {
            onSave(TimeTable.ClassBlock(subject, teacherName, type, classRoom))
        }) {
            Text("Save")
        }
    }
}
