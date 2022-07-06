package ru.grobikon.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.grobikon.backend.todo.entity.Category
import ru.grobikon.backend.todo.repo.CategoryRepository

// Такой подход полезен для будующих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service //все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun findById(id: Long): Category {
        return categoryRepository.findById(id).get() //т.к. возвращается Optional - можно получить объект методом get()
    }

    fun findAll(email: String): List<Category> {
        return categoryRepository.findByUserEmailOrderByTitleAsc(email)
    }

    fun add(category: Category): Category {
        return categoryRepository.save(category) //обновляем или создаём новый объект, если его не было
    }

    fun update(category: Category): Category {
        return categoryRepository.save(category) //обновляем или создаём новый объект, если его не было
    }

    fun deleteById(id: Long) {
        categoryRepository.deleteById(id)
    }

    fun findByTitle(title: String?, email: String): List<Category> {
        return categoryRepository.findByTitle(title, email)
    }
}