package ru.grobikon.backend.todo.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.grobikon.backend.todo.entity.Priority;

import java.util.List;

public interface PriorityRepository extends CrudRepository<Priority, Long> {

    //поиск категорий пользователя
    //doc: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    List<Priority> findByUserEmailOrderByTitleAsc(String email);

    //JPQL поиск значений по названию для конкретного пользователя
    @Query("SELECT p FROM Priority p where " +
            "(:title is null or :title=''" + //если передадим параметр title пустым, то выберутся все записи
            " or lower(p.title) like lower(concat('%', :title, '%'))) " + // если параметр title не пустой, то выполняем поиск по введенному значению
            " and p.user.email=:email " + // фильтрация для конкретного пользователя
            " order by p.title asc") //сортировка по названию
    List<Priority> findByTitle(@Param("title") String title, @Param("email") String email);
}
