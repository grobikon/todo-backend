package ru.grobikon.backend.todo.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grobikon.backend.todo.entity.Category;
import ru.grobikon.backend.todo.search.CategorySearchValues;
import ru.grobikon.backend.todo.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

// controller - это спец. бин, который обрабатывает веб запросы
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/all")
    public List<Category> findAll(@RequestBody String email) {
        return categoryService.findAll(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {

        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category) {

        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return new ResponseEntity("redundant param: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        categoryService.update(category);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //для удаления используем тип запроса DELETE и передаем ID для удаления
    //можно также использовать метод POST и передавать ID  в теле запроса
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        //можно обойтись и без try-catch, тогда будет возвращяться полная ошибка (stacktrace)
        //здесь показан пример, как можно обработать исключение и отправлять свой текст/статус
        try{
            categoryService.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(HttpStatus.OK); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        // проверка на обязательные параметры
        if (categorySearchValues.getEmail() == null || categorySearchValues.getEmail().trim().length() == 0) {
            return new ResponseEntity("redundant param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        //поиск категорий пользователя по назначению
        List<Category> list = categoryService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getEmail());

        return ResponseEntity.ok(list); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    public ResponseEntity<Category> findById(@RequestBody Long id) {

        Category category;

        try{
            category = categoryService.findById(id);
        }catch(NoSuchElementException e) { //если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }
}
