package ru.grobikon.backend.todo.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import ru.grobikon.backend.todo.entity.Priority
import ru.grobikon.backend.todo.entity.Stat

interface PriorityRepository : CrudRepository<Priority, Long> {
    //поиск категорий пользователя
    //doc: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    fun findByUserEmailOrderByTitleAsc(email: String): List<Priority>

    //JPQL поиск значений по названию для конкретного пользователя
    //сортировка по названию
    @Query(
        "SELECT p FROM Priority p where " +
                "(:title is null or :title=''" +  //если передадим параметр title пустым, то выберутся все записи
                " or lower(p.title) like lower(concat('%', :title, '%'))) " +  // если параметр title не пустой, то выполняем поиск по введенному значению
                " and p.user.email=:email " +  // фильтрация для конкретного пользователя
                " order by p.title asc"
    )
    fun findByTitle(@Param("title") title: String?, @Param("email") email: String): List<Priority>
}