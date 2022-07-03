package ru.grobikon.backend.todo.search;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskSearchValues {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;
    private String email;

    private Date dateFrom; // для задания периода по датам
    private Date dateTo;

    // постраничность
    private Integer pageNumber;
    private Integer pageSize;

    // сортировка
    private String sortColumn;
    private String sortDirection;

    // такие же названия должны быть у объекта на frontend
}
