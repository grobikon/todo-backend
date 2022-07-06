package ru.grobikon.backend.todo.search

//возможные значения, по которым можно искать категории
//email такое же название должно быть у объекта на frontend
//title для фильтрации значений конкретного пользователя
data class CategorySearchValues(val email: String, val title: String?)

//возможные значения, по которым можно искать категории
//такое же название должно быть у объекта на frontend
//для фильтрации значений конкретного пользователя
data class PrioritySearchValues(val title: String, val email: String?)