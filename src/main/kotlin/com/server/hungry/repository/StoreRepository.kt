package com.server.hungry.repository

import com.server.hungry.entity.Store
import com.server.hungry.entity.User
import org.springframework.data.repository.CrudRepository
import java.util.*

/**
 * StoreRepository
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-21
 */
interface StoreRepository: CrudRepository<Store, Long> {
    fun countByUserIdAndCategory(user_id: Long, category: String): Int
    fun countByUserId(user_id: Long): Int
    fun findAllByUserIdAndCategory(user_id: Long, category: String): List<Store>
    fun findAllByUserId(user_id: Long): List<Store>
    fun findByAddressNameAndUser(addressName: String, user: User): Optional<Store>
}