package eu.oncreate.bingie.data.local.model.tmdb

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Images(
    @ColumnInfo(name = "backdrop_sizes")
    val backdropSizes: List<String>,
    @ColumnInfo(name = "base_url")
    val baseUrl: String,
    @ColumnInfo(name = "logo_sizes")
    val logoSizes: List<String>,
    @ColumnInfo(name = "poster_sizes")
    val posterSizes: List<String>,
    @ColumnInfo(name = "profile_sizes")
    val profileSizes: List<String>,
    @ColumnInfo(name = "secure_base_url")
    val secureBaseUrl: String,
    @ColumnInfo(name = "still_sizes")
    val stillSizes: List<String>
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.tmdb.Images): Images {
            return Images(
                backdropSizes = item.backdropSizes,
                baseUrl = item.baseUrl,
                logoSizes = item.logoSizes,
                posterSizes = item.posterSizes,
                profileSizes = item.profileSizes,
                secureBaseUrl = item.secureBaseUrl,
                stillSizes = item.stillSizes
            )
        }

        fun toBe(images: Images): eu.oncreate.bingie.data.api.model.tmdb.Images {
            return eu.oncreate.bingie.data.api.model.tmdb.Images(
                backdropSizes = images.backdropSizes,
                baseUrl = images.baseUrl,
                logoSizes = images.logoSizes,
                posterSizes = images.posterSizes,
                profileSizes = images.profileSizes,
                secureBaseUrl = images.secureBaseUrl,
                stillSizes = images.stillSizes
            )
        }
    }
}
