package ru.grobikon.backend.todo.entity

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.*

/*

задачи пользователя

 */
@Entity
@Table(name = "task", schema = "todolist", catalog = "postgres")
class Task {
    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null
    val title: String? = null

    @Type(type = "org.hibernate.type.NumericBooleanType") // для автоматической конвертации числа в true/false
    val completed // 1 = true, 0 = false
            : Boolean? = null

    @Column(name = "task_date") // в БД поле называется task_date, т.к. нельзя использовать системное имя date
    val taskDate: Date? = null

    // задача может иметь только один приоритет (с обратной стороны - один и тот же приоритет может быть использоваться в множестве задач)
    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    val priority: Priority? = null

    // задача может иметь только одну категорию (с обратной стороны - одна и та же категория может быть использоваться в множестве задач)
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    val category: Category? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    val user // для какого пользователя задача
            : User? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val task = o as Task
        return id == task.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return title!!
    }
}