package eu.oncreate.bingie.data.local.model.tmdb

// @Entity
data class Poster(
    val parentId: Int,
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.tmdb.Poster, parentId: Int): Poster {
            return Poster(
                parentId = parentId,
                aspectRatio = item.aspectRatio,
                filePath = item.filePath,
                height = item.height,
                iso6391 = item.iso6391,
                voteAverage = item.voteAverage,
                voteCount = item.voteCount,
                width = item.width
            )
        }

        fun toBe(poster: Poster): eu.oncreate.bingie.data.api.model.tmdb.Poster {
            return eu.oncreate.bingie.data.api.model.tmdb.Poster(
                aspectRatio = poster.aspectRatio,
                filePath = poster.filePath,
                height = poster.height,
                iso6391 = poster.iso6391,
                voteAverage = poster.voteAverage,
                voteCount = poster.voteCount,
                width = poster.width
            )
        }
    }
}
