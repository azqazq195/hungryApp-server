package com.server.hungry.controller

import com.server.hungry.service.FileUploadService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

/**
 * FileUploadController
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-28
 */

@RestController
class FileUploadController {

    @Autowired
    private lateinit var fileUploadService: FileUploadService

    @PostMapping("/file")
    fun uploadImage(@RequestPart file: MultipartFile): String? {
        return fileUploadService.upload(file, "static")
    }


}