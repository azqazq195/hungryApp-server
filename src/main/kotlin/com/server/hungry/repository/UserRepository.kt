package com.server.hungry.repository

import com.server.hungry.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * UserRepository
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-20
 */
interface UserRepository: CrudRepository<User, Long> {
    fun findUserById(id: Long): User?
    fun findAllBy(): List<User>
    fun findUserByEmail(email: String): User?
    fun findUserByEmailAndPassword(email: String, password: String): User?

    fun findByEmail(email: String): Optional<User>
    fun findByPhone(phone:String): Optional<User>
    fun findByEmailAndPhone(email: String, phone: String): Optional<User>
}