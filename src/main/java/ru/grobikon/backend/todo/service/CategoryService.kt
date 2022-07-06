package ru.grobikon.backend.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.grobikon.backend.todo.entity.Category;
import ru.grobikon.backend.todo.repo.CategoryRepository;

import java.util.List;

// Такой подход полезен для будующих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service

//все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get(); //т.к. возвращается Optional - можно получить объект методом get()
    }

    public List<Category> findAll(String email) {
        return categoryRepository.findByUserEmailOrderByTitleAsc(email);
    }

    public Category add(Category category) {
        return categoryRepository.save(category); //обновляем или создаём новый объект, если его не было
    }

    public Category update(Category category) {
        return categoryRepository.save(category); //обновляем или создаём новый объект, если его не было
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findByTitle(String title, String email) {
        return categoryRepository.findByTitle(title, email);
    }
}
