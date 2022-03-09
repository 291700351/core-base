package io.github.lee.unsplash.model.domain

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity
data class PhotoBean(
    @PrimaryKey val id: String,
    val alt_description: String?,
    val blur_hash: String?,
    val color: String?,
    val created_at: String?,
    val description: String?,
    val height: Float,
    val likes: Int,
    val promoted_at: String?,
    val updated_at: String?,
    val width: Float,
) : Parcelable {
    @Suppress("PROPERTY_WONT_BE_SERIALIZED")
    @Ignore
    val urls: Urls? = null
    //    constructor():this()
}

@Keep
@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PhotoBean::class,
            parentColumns = ["id"],
            childColumns = ["photoId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index(value = ["photoId"], unique = true)]
)

data class Urls(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val small_s3: String,
    val thumb: String,
    var photoId: String?
) : Parcelable


data class PhotoAndUrl(
    @Embedded val photo: PhotoBean,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoId"
    ) val url: Urls?
)
//
//@Keep
//@Parcelize
//@Entity
//data class PhotoBean2(
//    @PrimaryKey val id: String ,
//    val alt_description: String?,
//    val blur_hash: String?,
//    val color: String?,
//    val created_at: String?,
//    val description: String?,
//    val height: Float?,
//    val likes: Int?,
//    val promoted_at: String?,
//    val updated_at: String?,
//    val width: Float,
//) : Parcelable
//
//
//
//@Keep
//@Parcelize
//@Entity(
//    foreignKeys = [
//        ForeignKey(
//            entity = PhotoBean2::class,
//            parentColumns = ["id"],
//            childColumns = ["photoId"],
//            onDelete = CASCADE,
//            onUpdate = CASCADE
//        )
//    ],
//    indices = [Index(value = ["photoId"], unique = true)]
//)
//data class Urls2(
//    @PrimaryKey(autoGenerate = true) val id: Int,
//    val full: String,
//    val raw: String,
//    val regular: String,
//    val small: String,
//    val small_s3: String,
//    val thumb: String,
//    val photoId: String
//) : Parcelable

//===Desc:=====================================================================================
