package ru.grobikon.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.grobikon.backend.todo.entity.Priority
import ru.grobikon.backend.todo.repo.PriorityRepository

// Всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service //все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
class PriorityService(private val priorityRepository: PriorityRepository) {
    fun findById(id: Long): Priority {
        return priorityRepository.findById(id).get() //т.к. возвращается Optional - можно получить объект методом get()
    }

    fun findAll(email: String): List<Priority> {
        return priorityRepository.findByUserEmailOrderByTitleAsc(email)
    }

    fun add(priority: Priority): Priority {
        return priorityRepository.save(priority) //обновляем или создаём новый объект, если его не было
    }

    fun update(priority: Priority): Priority {
        return priorityRepository.save(priority) //обновляем или создаём новый объект, если его не было
    }

    fun deleteById(id: Long) {
        priorityRepository.deleteById(id)
    }

    fun findByTitle(title: String?, email: String): List<Priority> {
        return priorityRepository.findByTitle(title, email)
    }
}