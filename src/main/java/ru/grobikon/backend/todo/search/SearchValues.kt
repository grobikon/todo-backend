package ru.grobikon.backend.todo.search

import java.util.*

//возможные значения, по которым можно искать категории
//email такое же название должно быть у объекта на frontend
//title для фильтрации значений конкретного пользователя
data class CategorySearchValues(val email: String, val title: String?)

//возможные значения, по которым можно искать приоритетов
//такое же название должно быть у объекта на frontend
//для фильтрации значений конкретного пользователя
data class PrioritySearchValues(val title: String, val email: String?)

// возможные значения, по которым можно искать задачи + значения сортировки
data class TaskSearchValues(
    val email: String,
    // постраничность
    val pageNumber: Int,
    val pageSize: Int,
    // сортировка
    val sortColumn: String,
    val sortDirection: String // такие же названия должны быть у объекта на frontend
) {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    val title: String? = null
    val completed: Int? = null
    val priorityId: Long? = null
    val categoryId: Long? = null
    val dateFrom: Date? = null      // для задания периода по датам
    val dateTo: Date? = null
}