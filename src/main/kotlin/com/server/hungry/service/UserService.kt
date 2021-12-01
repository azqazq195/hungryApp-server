package com.server.hungry.service

import com.server.hungry.dto.CreateUserDTO
import com.server.hungry.dto.LoginUserDTO
import com.server.hungry.dto.UpdateUserDTO
import com.server.hungry.entity.User
import com.server.hungry.repository.UserRepository
import com.server.hungry.response.Response
import com.server.hungry.response.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * UserService
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-20
 */
@Component
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Transactional
    fun getUsers(): Response {
        val users = userRepository.findAllBy()
        val response = Response(result = Result.SUCCESS.name, message = null)
        response.setDataList(users.map { it.toReadUserDto() }.toMutableList())
        return response
    }

    @Transactional
    fun getUser(id: Long): Response {
        val user: User? = userRepository.findUserById(id)
        user ?: return Response(result = Result.FAILURE.name, message = "존재하지 않는 유저 입니다.")
        val response = Response(result = Result.SUCCESS.name, message = null)
        response.addData(user.toReadUserDto())
        return response
    }

    @Transactional
    fun createUser(createUserDTO: CreateUserDTO): Response {
        if(userRepository.findByEmail(createUserDTO.email).isPresent) {
            return Response(result = Result.FAILURE.name, message = "이메일이 중복 되었습니다.")
        }
        if(userRepository.findByPhone(createUserDTO.phone).isPresent) {
            return Response(result = Result.FAILURE.name, message = "핸드폰 번호가 중복 되었습니다.")
        }
        val user = userRepository.save(createUserDTO.toEntity())
        val response = Response(result = Result.SUCCESS.name, message = null)
        response.addData(user.toReadUserDto())
        return response
    }

    @Transactional
    fun login(loginUserDTO: LoginUserDTO): Response {
        var user: User? = userRepository.findUserByEmail(loginUserDTO.email)
        user ?: return Response(result = Result.FAILURE.name, message = "존재하지 않는 아이디 입니다.")
        user = userRepository.findUserByEmailAndPassword(loginUserDTO.email, loginUserDTO.password)
        user ?: return Response(result = Result.FAILURE.name, message = "비밀번호가 일치하지 않습니다.")
        return Response(result = Result.SUCCESS.name, message = null, user)
    }

    @Transactional
    fun deleteUser(id: Long): Response {
        if (!userRepository.existsById(id))
            return Response(result = Result.FAILURE.name, message = "존재하지 않는 유저 입니다.")
        userRepository.deleteById(id)
        return if (!userRepository.existsById(id))
            Response(result = Result.SUCCESS.name, message = "삭제 완료.")
        else
            Response(result = Result.FAILURE.name, message = "삭제 실패.")
    }

    fun findEmail(phone: String): Response {
        return if (userRepository.findByPhone(phone).isPresent) {
            val user = userRepository.findByPhone(phone).get()
            Response(result = Result.SUCCESS.name, message = "이메일 찾기.", data = user.email)
        } else {
            Response(result = Result.FAILURE.name, message = "존재하지 않는 유저 입니다.")
        }
    }

    fun findPassword(email: String, phone: String): Response {
        return if (userRepository.findByEmailAndPhone(email, phone).isPresent) {
            val user = userRepository.findByEmailAndPhone(email, phone).get()
            Response(result = Result.SUCCESS.name, message = "비밀번호 찾기.", data = user.password)
        } else {
            Response(result = Result.FAILURE.name, message = "존재하지 않는 유저 입니다.")
        }
    }

    fun updateUser(updateUserDTO: UpdateUserDTO): Response {
        return if (userRepository.findById(updateUserDTO.id).isPresent) {
            val user = userRepository.findById(updateUserDTO.id).get()
            user.photo = updateUserDTO.photo
            userRepository.save(user)
            Response(result = Result.SUCCESS.name, message = "업데이트 성공.", data = updateUserDTO.id)
        } else {
            Response(result = Result.FAILURE.name, message = "업데이트 실패.", data = updateUserDTO.id)
        }
    }

    fun checkEmail(email: String): Response {
        return if (userRepository.findByEmail(email).isPresent) {
            Response(result = Result.FAILURE.name, message = "이메일이 중복 되었습니다.")
        } else {
            Response(result = Result.SUCCESS.name, message = "생성 가능한 이메일 입니다.")
        }
    }

    fun checkPhone(phone: String): Response {
        return if (userRepository.findByPhone(phone).isPresent) {
            Response(result = Result.FAILURE.name, message = "핸드폰 번호가 중복 되었습니다.")
        } else {
            Response(result = Result.SUCCESS.name, message = "생성 가능한 핸드폰 번호 입니다.")
        }
    }
}