package ru.grobikon.backend.todo.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.grobikon.backend.todo.entity.Priority;
import ru.grobikon.backend.todo.search.PrioritySearchValues;
import ru.grobikon.backend.todo.service.PriorityService;

import java.util.List;
import java.util.NoSuchElementException;

// controller - это спец. бин, который обрабатывает веб запросы
@RestController
@RequestMapping("/priority")
public class PriorityController {

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    @PostMapping("/all")
    public List<Priority> findAll(@RequestBody String email) {
        return priorityService.findAll(email);
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority category) {

        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создаётся автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Priority category) {

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
        priorityService.update(category);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    //для удаления используем тип запроса DELETE и передаем ID для удаления
    //можно также использовать метод POST и передавать ID  в теле запроса
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {

        //можно обойтись и без try-catch, тогда будет возвращяться полная ошибка (stacktrace)
        //здесь показан пример, как можно обработать исключение и отправлять свой текст/статус
        try{
            priorityService.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(HttpStatus.OK); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //поиск по любым параметрам PrioritySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues categorySearchValues) {

        // проверка на обязательные параметры
        if (categorySearchValues.getEmail() == null || categorySearchValues.getEmail().trim().length() == 0) {
            return new ResponseEntity("redundant param: email", HttpStatus.NOT_ACCEPTABLE);
        }

        //поиск категорий пользователя по назначению
        List<Priority> list = priorityService.findByTitle(categorySearchValues.getTitle(), categorySearchValues.getEmail());

        return ResponseEntity.ok(list); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }

    //параметр id передаются не в BODY запроса, а в самом URL
    @PostMapping("/id")
    public ResponseEntity<Priority> findById(@RequestBody Long id) {

        Priority category;

        try{
            category = priorityService.findById(id);
        }catch(NoSuchElementException e) { //если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category); // просто отправляем статус 200 без объектов (операция прошла успешно)
    }
}
