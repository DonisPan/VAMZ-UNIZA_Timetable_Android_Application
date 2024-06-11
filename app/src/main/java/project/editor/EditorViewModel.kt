package project.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import project.timetable.TimeTable.ClassBlock
import project.Subjects
import project.LectureType

class EditorViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    var subject: Subjects by mutableStateOf(savedStateHandle["subject"] ?: Subjects.entries[0])
        private set
    var teacherName: String by mutableStateOf(savedStateHandle["teacherName"] ?: subject.teachers.first())
        private set
    var type: LectureType? by mutableStateOf(savedStateHandle["type"] ?: LectureType.entries.first())
        private set
    var classRoom: String by mutableStateOf(savedStateHandle["classRoom"] ?: "")
        private set

    fun updateSubject(newSubject: Subjects)
    {
        subject = newSubject
        teacherName = newSubject.teachers.first()
    }

    fun updateTeacher(newTeacher: String)
    {
        teacherName = newTeacher
    }

    fun updateType(newType: LectureType?)
    {
        type = newType
    }

    fun updateClassRoom(newClassRoom: String)
    {
        classRoom = newClassRoom
    }

    fun toClassBlock() : ClassBlock
    {
        return ClassBlock(subject, teacherName, type, classRoom)
    }
}
