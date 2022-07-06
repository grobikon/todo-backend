package ru.grobikon.backend.todo.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.persistence.*

/*

справочноное значение - категория пользователя
может использовать для своих задач

 */
@Entity
@Table(name = "category", schema = "todolist", catalog = "postgres")
class Category {
    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null
    val title: String? = null

    @Column(
        name = "completed_count",
        updatable = false
    ) // т.к. это поле высчитывается автоматически в триггерах - вручную его не обновляем (updatable = false)
    val completedCount: Long? = null

    @Column(
        name = "uncompleted_count",
        updatable = false
    ) // т.к. это поле высчитывается автоматически в триггерах - вручную его не обновляем (updatable = false)
    val uncompletedCount: Long? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связаны эти 2 объекта (foreign key)
    val user: User? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val category = o as Category
        return id == category.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }
}