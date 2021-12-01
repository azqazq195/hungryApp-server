package com.server.hungry.entity

import com.server.hungry.dto.ReadStoreDTO
import org.hibernate.Hibernate
import javax.persistence.*

/**
 * Store
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-21
 */
@Entity
data class Store(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String?,
    val review: String?,
    val rating: Double?,
    val addressName: String?,
    val categoryGroupName: String?,
    val categoryGroupCode: String?,
    val phone: String?,
    val placeName: String?,
    val placeUrl: String?,
    val roadAddressName: String?,
    val x: Double?,
    val y: Double?,
    val category: String?,

    @ManyToMany(cascade = [CascadeType.ALL])
    val photo: MutableList<Photo> = mutableListOf(),

    @ManyToOne
    val user: User
) {
    fun toReadStoreDto(): ReadStoreDTO {
        return ReadStoreDTO(
            id = id,
            name = name,
            review = review,
            rating = rating,
            addressName = addressName,
            categoryGroupName = categoryGroupName,
            categoryGroupCode = categoryGroupCode,
            phone = phone,
            placeName = placeName,
            placeUrl = placeUrl,
            roadAddressName = roadAddressName,
            x = x,
            y = y,
            category = category,
            photo = photo,
            user = user.toReadUserDto()
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Store

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , review = $review , rating = $rating )"
    }
}