package com.server.hungry.entity

import com.server.hungry.dto.CreateUserDTO
import com.server.hungry.dto.ReadUserDTO
import org.hibernate.Hibernate
import java.time.OffsetDateTime
import javax.persistence.*

/**
 * User
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-20
 */
@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String,
    @Column(unique = true)
    val email: String,
    val password: String,
    @Column(unique = true)
    val phone: String,
    val createdDate: OffsetDateTime,
    var updatedDate: OffsetDateTime? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    var photo: Photo? = null,
) {
    fun toReadUserDto(): ReadUserDTO {
        return ReadUserDTO(
            id = id,
            name = name,
            email = email,
            phone = phone,
            createdDate = createdDate,
            updatedDate = updatedDate,
            photo = photo,
        )
    }

    fun toCreateUserDto(): CreateUserDTO {
        return CreateUserDTO(
            name = name,
            phone = phone,
            email = email,
            password = password,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
