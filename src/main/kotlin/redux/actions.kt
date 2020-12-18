package redux

import data.*

class ChangePresent(val lessonID: Int, val studentID: Int) : RAction

class AddStudent(val student: Student) : RAction

class RemoveStudent(val id: Int) : RAction

class ChangeStudent(val id: Int, val newStudent: Student) : RAction

class AddLesson(val lesson: Lesson) : RAction

class RemoveLesson(val id: Int) : RAction

class ChangeLesson(val id: Int, val newLesson: Lesson) : RAction
