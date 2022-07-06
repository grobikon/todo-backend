package ru.grobikon.backend.todo.entity

import java.util.*
import javax.persistence.*

/*

Все доступные роли, которые будут привязаны к пользователю

*/
@Entity
@Table(name = "role_data", schema = "todolist", catalog = "postgres")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    // название роли
    val name: String? = null

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val users: Set<User>? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val role = o as Role
        return id == role.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

    override fun toString(): String {
        return name!!
    }
}