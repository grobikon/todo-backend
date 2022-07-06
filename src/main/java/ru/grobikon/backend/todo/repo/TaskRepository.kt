package ru.grobikon.backend.todo.repo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.grobikon.backend.todo.entity.Stat
import ru.grobikon.backend.todo.entity.Task
import java.util.*

//принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
interface TaskRepository : CrudRepository<Task, Long> {
    // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    @Query(
        "SELECT t FROM Task t where " +
                "(:title is null or :title='' or lower(t.title) like lower(concat('%', :title,'%'))) and" +
                "(:completed is null or t.completed=:completed) and " +  // учитываем, что параметр может быть null или пустым
                "(:priorityId is null or t.priority.id=:priorityId) and " +
                "(:categoryId is null or t.category.id=:categoryId) and " +
                "(:categoryId is null or t.category.id=:categoryId) and " +
                "(" +
                "(cast(:dateFrom as timestamp) is null or t.taskDate>=:dateFrom) and " +
                "(cast(:dateTo as timestamp) is null or t.taskDate<=:dateTo)" +
                ") and " +
                "(t.user.email=:email)"
    )
    fun findByParams(
        @Param("title") title: String?,
        @Param("completed") completed: Boolean?,
        @Param("priorityId") priorityId: Long?,
        @Param("categoryId") categoryId: Long?,
        @Param("email") email: String,
        @Param("dateFrom") dateFrom: Date?,
        @Param("dateTo") dateTo: Date?,
        pageable: Pageable
    ): Page<Task>

    // поиск всех задач конкретного пользователя
    fun findByUserEmailOrderByTitleAsc(email: String?): List<Task>
}