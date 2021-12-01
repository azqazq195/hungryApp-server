package com.server.hungry.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


/**
 * FileUploadService
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-28
 */
@Component
class FileUploadService {

    @Autowired
    private lateinit var amazonS3Client: AmazonS3Client

    @Value("\${cloud.aws.s3.bucket}")
    val bucket : String? = null

    @Throws(IOException::class)
    fun upload(multipartFile: MultipartFile?, dirName: String?): String? {
        val uploadFile: File? = multipartFile?.let {
            convert(it) // 파일 변환할 수 없으면 에러
                .orElseThrow { IllegalArgumentException("error: MultipartFile -> File convert fail") }
        }
        return dirName?.let { uploadFile?.let { it1 -> upload(it1, it) } }
    }

    // S3로 파일 업로드하기
    private fun upload(uploadFile: File, dirName: String): String? {
        val fileName = dirName + "/" + UUID.randomUUID() + uploadFile.name // S3에 저장된 파일 이름
        val uploadImageUrl: String = putS3(uploadFile, fileName) + ".png" // s3로 업로드
        removeNewFile(uploadFile)
        return uploadImageUrl
    }

    // S3로 업로드
    private fun putS3(uploadFile: File, fileName: String): String {
        amazonS3Client.putObject(
            PutObjectRequest(
                bucket,
                fileName,
                uploadFile
            ).withCannedAcl(CannedAccessControlList.PublicRead)
        )
        return amazonS3Client.getUrl(bucket, fileName).toString()
    }

    // 로컬에 저장된 이미지 지우기
    private fun removeNewFile(targetFile: File) {
        if (targetFile.delete()) {
            return
        }
    }

    // 로컬에 파일 업로드 하기
    @Throws(IOException::class)
    private fun convert(file: MultipartFile): Optional<File> {
        val convertFile = File(System.getProperty("user.dir") + "/" + file.originalFilename)
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File 이 생성됨 (경로가 잘못되었다면 생성 불가능)
            FileOutputStream(convertFile).use { fos ->  // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.bytes)
            }
            return Optional.of(convertFile)
        }
        return Optional.empty()
    }
}