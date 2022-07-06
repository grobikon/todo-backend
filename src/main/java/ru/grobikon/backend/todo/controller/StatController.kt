package ru.grobikon.backend.todo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.grobikon.backend.todo.entity.Stat
import ru.grobikon.backend.todo.service.StatService

/*

Чтобы дать меньше шансов для взлома (например, CSRF атак): POST/PUT запросы могут изменять/фильтровать закрытые данные, а GET запросы - для получения незащищенных данных
Т.е. GET-запросы не должны использоваться для изменения/получения секретных данных

Если возникнет exception - вернется код  500 Internal Server Error, поэтому не нужно все действия оборачивать в try-catch

Используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON,
иначе пришлось бы добавлять лишние объекты в код, использовать @ResponseBody для ответа, указывать тип отправки JSON

Названия методов могут быть любыми, главное не дублировать их имена и URL mapping

*/
// controller - это спец. бин, который обрабатывает веб запросы
@RestController
class StatController     // автоматическое внедрение экземпляра класса через конструктор
// не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended"
    (  //сервис для доступа к данным (напрямую к репозиториям не обращаемся)
    private val statService: StatService
) {
    // для статистики всегда получаем только одну строку с id=1 (согласно таблице БД)
    @PostMapping("/stat")
    fun findByEmail(@RequestBody email: String?): ResponseEntity<Stat> {

        // можно не использовать ResponseEntity, а просто вернуть коллекцию, код все равно будет 200 ОК
        return ResponseEntity.ok(statService.findStat(email!!))
    }
}