package ru.grobikon.backend.todo.controller

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.grobikon.backend.todo.entity.Category
import ru.grobikon.backend.todo.search.CategorySearchValues
import ru.grobikon.backend.todo.service.CategoryService

// controller - это спец. бин, который обрабатывает веб запросы
@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping("/all")
    fun findAll(@RequestBody email: String): List<Category> {
        return categoryService.findAll(email)
    }

    @PostMapping("/add")
    fun add(@RequestBody category: Category): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (category.id != null && category.id != 0L) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return ResponseEntity<Any>("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (category.title == null || category.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        return ResponseEntity.ok(categoryService.add(category))
    }

    @PutMapping("/update")
    fun update(@RequestBody category: Category): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (category.id == null || category.id == 0L) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return ResponseEntity<Any>("redundant param: id", HttpStatus.NOT_ACCEPTABLE)
        }

        // если передали пустое значение title
        if (category.title == null || category.title.trim().isEmpty()) {
            return ResponseEntity<Any>("missed param: title", HttpStatus.NOT_ACCEPTABLE)
        }

        // save работает как на добавление, так и на обновление
        categoryService.update(category)
        return ResponseEntity.ok(HttpStatus.OK)
    }

    //для удаления используем тип запроса DELETE и передаем ID для удаления
    //можно также использовать метод POST и передавать ID  в теле запроса
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {

        //можно обойтись и без try-catch, тогда будет возвращяться полная ошибка (stacktrace)
        //здесь показан пример, как можно обработать исключение и отправлять свой текст/статус
        try {
            categoryService.deleteById(id)
        } catch (e: EmptyResultDataAccessException) {
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(HttpStatus.OK) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    fun search(@RequestBody categorySearchValues: CategorySearchValues): ResponseEntity<Any> {

        // проверка на обязательные параметры
        if (categorySearchValues.email == null || categorySearchValues.email.trim().isEmpty()) {
            return ResponseEntity<Any>("redundant param: email", HttpStatus.NOT_ACCEPTABLE)
        }

        //поиск категорий пользователя по назначению
        val list = categoryService.findByTitle(categorySearchValues.title, categorySearchValues.email)
        return ResponseEntity.ok(list) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    fun findById(@RequestBody id: Long): ResponseEntity<Any> {
        val category: Category = try {
            categoryService.findById(id)
        } catch (e: NoSuchElementException) { //если объект не будет найден
            e.printStackTrace()
            return ResponseEntity<Any>("id=$id not found", HttpStatus.NOT_ACCEPTABLE)
        }
        return ResponseEntity.ok(category) // просто отправляем статус 200 без объектов (операция прошла успешно)
    }
}