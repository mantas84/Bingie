package eu.oncreate.bingie.data.local.model.tmdb

data class Backdrop(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int
) {

    companion object {
        fun toLocal(item: eu.oncreate.bingie.data.api.model.tmdb.Backdrop): Backdrop {
            return Backdrop(
                aspectRatio = item.aspectRatio,
                filePath = item.filePath,
                height = item.height,
                iso6391 = item.iso6391,
                voteAverage = item.voteAverage,
                voteCount = item.voteCount,
                width = item.width
            )
        }

        fun toBe(backdrop: Backdrop): eu.oncreate.bingie.data.api.model.tmdb.Backdrop {
            return eu.oncreate.bingie.data.api.model.tmdb.Backdrop(
                aspectRatio = backdrop.aspectRatio,
                filePath = backdrop.filePath,
                height = backdrop.height,
                iso6391 = backdrop.iso6391,
                voteAverage = backdrop.voteAverage,
                voteCount = backdrop.voteCount,
                width = backdrop.width
            )
        }
    }
}
