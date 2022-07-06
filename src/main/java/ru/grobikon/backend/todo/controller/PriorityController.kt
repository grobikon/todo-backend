package ru.grobikon.backend.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.grobikon.backend.todo.entity.Priority
import ru.grobikon.backend.todo.search.PrioritySearchValues
import ru.grobikon.backend.todo.service.PriorityService

// controller - это спец. бин, который обрабатывает веб запросы
@RestController
@RequestMapping("/priority")
class PriorityController(private val priorityService: PriorityService) {
    @PostMapping("/all")
    fun findAll(@RequestBody email: String?): List<Priority> {
        return priorityService.findAll(email!!)
    }

    @PostMapping("/add")
    fun add(@RequestBody priority: Priority): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (priority.id != null && priority.id != 0L) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (priority.title == null || priority.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(priorityService.add(priority))
    }

    @PutMapping("/update")
    fun update(@RequestBody priority: Priority): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (priority.id == null || priority.id == 0L) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return ResponseEntity<Any>("redundant param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (priority.title == null || priority.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        // save работает как на добавление, так и на обновление
        priorityService.update(priority)
        return ResponseEntity.ok(HttpStatus.OK)
    }

    //для удаления используем тип запроса DELETE и передаем ID для удаления
    //можно также использовать метод POST и передавать ID  в теле запроса
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        //можно обойтись и без try-catch, тогда будет возвращяться полная ошибка (stacktrace)
        //здесь показан пример, как можно обработать исключение и отправлять свой текст/статус
        try {
            priorityService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(HttpStatus.OK) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //поиск по любым параметрам PrioritySearchValues
    @PostMapping("/search")
    fun search(@RequestBody categorySearchValues: PrioritySearchValues): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (categorySearchValues.email == null || categorySearchValues.email.trim().isEmpty()) {
            return ResponseEntity<Any>("redundant param: email", HttpStatus.NOT_ACCEPTABLE)
        }

        //поиск категорий пользователя по назначению
        val list = priorityService.findByTitle(categorySearchValues.title, categorySearchValues.email)
        return ResponseEntity.ok(list) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {
        val category: Priority = try {
            priorityService.findById(id)
        } catch (e: NoSuchElementException) { //если объект не будет найден
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(category) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }
}