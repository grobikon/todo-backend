package ru.grobikon.backend.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grobikon.backend.todo.entity.Stat;
import ru.grobikon.backend.todo.entity.Task;
import ru.grobikon.backend.todo.repo.StatRepository;
import ru.grobikon.backend.todo.repo.TaskRepository;

import java.util.Date;
import java.util.List;

// Всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service

//все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
public class TaskService {

    private final TaskRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public List<Task> findAll(String email) {
        return repository.findByUserEmailOrderByTitleAsc(email);
    }

    public Task add(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public Task update(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public Page<Task> findByParams(String text, Boolean completed, Long priorityId, Long categoryId, String email, Date dateFrom, Date dateTo, PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, email, dateFrom, dateTo, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).get(); // т.к. возвращается Optional - можно получить объект методом get()
    }
}
