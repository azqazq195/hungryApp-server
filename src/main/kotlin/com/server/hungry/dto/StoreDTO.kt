package com.server.hungry.dto

import com.server.hungry.entity.Photo
import com.server.hungry.entity.Store
import com.server.hungry.entity.User

/**
 * StoreDTO
 * 주소
 * GitHub : http://github.com/azqazq195
 * Created by azqazq195@gmail.com on 2021-11-21
 */
data class ReadStoreDTO(
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

    val photo: MutableList<Photo>,
    val user: ReadUserDTO
)

data class UpdateStoreDTO(
    val id: Long,
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

    val photo: MutableList<Photo>?,
    var user: User
) {
    fun toUpdateEntity(store: Store): Store {
        return Store(
            id = store.id,
            name = name ?: store.name,
            review = review ?: store.review,
            rating = rating ?: store.rating,
            addressName = addressName ?: store.addressName,
            categoryGroupName = categoryGroupName ?: store.categoryGroupName,
            categoryGroupCode = categoryGroupCode ?: store.categoryGroupCode,
            phone = phone ?: store.phone,
            placeName = placeName ?: store.placeName,
            placeUrl = placeUrl ?: store.placeUrl,
            roadAddressName = roadAddressName ?: store.roadAddressName,
            x = x ?: store.x,
            y = y ?: store.y,
            category = category ?: store.category,
            photo = photo ?: store.photo,
            user = store.user
        )
    }
}

data class CreateStoreDTO(
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

    val photo: MutableList<Photo>,
    var user: User
) {
    fun toEntity(): Store {
        return Store(
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
            user = user
        )
    }
    fun toUpdateEntity(store: Store): Store {
        return Store(
            id = store.id,
            name = name ?: store.name,
            review = review ?: store.review,
            rating = rating ?: store.rating,
            addressName = addressName ?: store.addressName,
            categoryGroupName = categoryGroupName ?: store.categoryGroupName,
            categoryGroupCode = categoryGroupCode ?: store.categoryGroupCode,
            phone = phone ?: store.phone,
            placeName = placeName ?: store.placeName,
            placeUrl = placeUrl ?: store.placeUrl,
            roadAddressName = roadAddressName ?: store.roadAddressName,
            x = x ?: store.x,
            y = y ?: store.y,
            category = category ?: store.category,
            photo = photo ?: store.photo,
            user = store.user
        )
    }
}