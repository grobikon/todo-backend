package ru.grobikon.backend.todo.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.grobikon.backend.todo.repo.StatRepository
import ru.grobikon.backend.todo.entity.Stat

// Всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работает с транзакциями)
@Service //все методы класса должны выполняться без ошибки, чтобы транзакция завершилась
//если в методе возникает исключение - все выполненные операции из данного метода откатятся (Rollback)
@Transactional
class StatService(  //сервис имеет право обращаться к репозиторию (БД)
    private val statRepository: StatRepository
) {
    fun findStat(email: String): Stat {
        return statRepository.findByUserEmail(email)
    }
}