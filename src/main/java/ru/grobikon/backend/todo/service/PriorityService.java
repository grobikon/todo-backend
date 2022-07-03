package ru.grobikon.backend.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grobikon.backend.todo.entity.Priority;
import ru.grobikon.backend.todo.repo.PriorityRepository;

import java.util.List;

// Всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service

//все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public Priority findById(Long id) {
        return priorityRepository.findById(id).get(); //т.к. возвращается Optional - можно получить объект методом get()
    }

    public List<Priority> findAll(String email) {
        return priorityRepository.findByUserEmailOrderByTitleAsc(email);
    }

    public Priority add(Priority priority) {
        return priorityRepository.save(priority); //обновляем или создаём новый объект, если его не было
    }

    public Priority update(Priority priority) {
        return priorityRepository.save(priority); //обновляем или создаём новый объект, если его не было
    }

    public void deleteById(Long id) {
        priorityRepository.deleteById(id);
    }

    public List<Priority> findByTitle(String title, String email) {
        return priorityRepository.findByTitle(title, email);
    }
}
