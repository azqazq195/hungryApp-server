package com.server.hungry.service

import com.server.hungry.dto.CreateStoreDTO
import com.server.hungry.dto.UpdateStoreDTO
import com.server.hungry.repository.StoreRepository
import com.server.hungry.repository.UserRepository
import com.server.hungry.response.Response
import com.server.hungry.response.Result
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * StoreService
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-21
 */
@Component
class StoreService {

    @Autowired
    private lateinit var storeRepository: StoreRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional
    fun getAllStores(): Response {
        val total = storeRepository.count()
        val stores = storeRepository.findAll()
        val response = Response(result = Result.SUCCESS.name, message = null)
        response.setDataList(stores.map { it.toReadStoreDto() }.toMutableList())
        response.addMeta("total", total)
        return response
    }

    @Transactional
    fun getStores(user_id: Long, category: String?): Response {
        val total =
            if (category == null) {
                storeRepository.countByUserId(user_id)
            } else {
                storeRepository.countByUserIdAndCategory(user_id, category)
            }

        val stores =
            if (category == null) {
                storeRepository.findAllByUserId(user_id)
            } else {
                storeRepository.findAllByUserIdAndCategory(user_id, category)
            }

        val response = Response(result = Result.SUCCESS.name, message = null)
        response.setDataList(stores.map { it.toReadStoreDto() }.toMutableList())
        response.addMeta("total", total)
        return response
    }

    @Transactional
    fun getStore(id: Long): Response {
        if (!storeRepository.existsById(id))
            return Response(result = Result.FAILURE.name, message = "존재하지 않는 가게 입니다.")
        val store = storeRepository.findById(id).get()
        val response = Response(result = Result.SUCCESS.name, message = null)
        response.addData(store.toReadStoreDto())
        return response
    }

    @Transactional
    fun createStore(createStoreDTO: CreateStoreDTO): Response {
        createStoreDTO.user.id ?: return Response(result = Result.FAILURE.name, message = "user id 값이 없습니다.")
        createStoreDTO.user =
            if (userRepository.findById(createStoreDTO.user.id!!).isPresent) {
                userRepository.findById(createStoreDTO.user.id!!).get()
            } else {
                return Response(result = Result.FAILURE.name, message = "존재하지 않는 유저 입니다.")
            }

        createStoreDTO.addressName ?: return Response(result = Result.FAILURE.name, message = "AddressName 누락.")

        return if (storeRepository.findByAddressNameAndUser(createStoreDTO.addressName, createStoreDTO.user).isPresent) {
            val currentStore =
                storeRepository.findByAddressNameAndUser(createStoreDTO.addressName, createStoreDTO.user).get()
            val updatedStore = storeRepository.save(createStoreDTO.toUpdateEntity(currentStore))
            val response = Response(result = Result.SUCCESS.name, message = "업데이트 완료.")
            response.addData(updatedStore.toReadStoreDto())
            response
        } else {
            val createdStore = storeRepository.save(createStoreDTO.toEntity())
            val response = Response(result = Result.SUCCESS.name, message = "생성 완료.")
            response.addData(createdStore.toReadStoreDto())
            response
        }
    }

    @Transactional
    fun deleteStore(id: Long): Response {
        if (!storeRepository.existsById(id))
            return Response(result = Result.FAILURE.name, message = "존재하지 않는 가게 입니다.")
        storeRepository.deleteById(id)
        return if (!storeRepository.existsById(id))
            Response(result = Result.SUCCESS.name, message = "삭제 완료.")
        else
            Response(result = Result.FAILURE.name, message = "삭제 실패.")
    }

    @Transactional
    fun updateStore(updateStoreDTO: UpdateStoreDTO): Response {
        return if (storeRepository.findById(updateStoreDTO.id).isPresent) {
            val store = storeRepository.findById(updateStoreDTO.id).get()
            storeRepository.save(updateStoreDTO.toUpdateEntity(store))
            Response(result = Result.SUCCESS.name, message = "업데이트 완료.")
        } else {
            Response(result = Result.FAILURE.name, message = "존재하지 않는 유저입니다.")
        }
    }
}