package ru.grobikon.backend.todo.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.grobikon.backend.todo.entity.Category

@Repository
interface CategoryRepository : CrudRepository<Category, Long> {
    //поиск категорий пользователя(по названию)
    //doc: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    fun findByUserEmailOrderByTitleAsc(email: String): List<Category>

    //JPQL поиск значений по названию для конкретного пользователя
    //сортировка по названию
    @Query(
        "SELECT c FROM Category c where " +
                "(:title is null or :title=''" +  //если передадим параметр title пустым, то выберутся все записи
                " or lower(c.title) like lower(concat('%', :title, '%'))) " +  // если параметр title не пустой, то выполняем поиск по введенному значению
                " and c.user.email=:email " +  // фильтрация для конкретного пользователя
                " order by c.title asc"
    )
    fun findByTitle(@Param("title") title: String?, @Param("email") email: String): List<Category>
}