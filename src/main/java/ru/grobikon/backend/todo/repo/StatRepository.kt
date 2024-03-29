package ru.grobikon.backend.todo.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.grobikon.backend.todo.entity.Stat

//принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
interface StatRepository : CrudRepository<Stat, Long> {
    fun findByUserEmail(email: String): Stat // всегда получаем только 1 объект, т.к. 1 пользователь содержит только 1 строку статистики(связь "один к одному")
}