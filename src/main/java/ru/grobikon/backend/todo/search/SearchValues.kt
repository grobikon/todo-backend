package ru.grobikon.backend.todo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

//возможные значения, по которым можно искать категории
public class CategorySearchValues {

    private String title; //такое же название должно быть у объекта на frontend
    private String email; //для фильтрации значений конкретного пользователя
}
