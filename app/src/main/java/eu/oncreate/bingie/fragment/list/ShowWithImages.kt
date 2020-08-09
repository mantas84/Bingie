package eu.oncreate.bingie.fragment.list

import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.tmdb.Images
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages

data class ShowWithImages(
    val searchResultItem: SearchResultItem,
    val tmdbImages: TmdbImages?,
    val tmdbImagesConfiguration: Images?,
    val fanartImages: FanartImages?
) {

    fun getImage(): String? = getTmdbImage() ?: getFanartImage()

    private fun getTmdbImage(): String? {
        val baseUrl = tmdbImagesConfiguration?.secureBaseUrl
        val fileSize = tmdbImagesConfiguration?.posterSizes?.getOrNull(3) ?: "original"
        val imagePath = tmdbImages?.backdrops?.getOrNull(0)?.filePath
        return "$baseUrl$fileSize/$imagePath".takeUnless { baseUrl == null || imagePath == null }
    }

    private fun getFanartImage(): String? {
        val clearart = fanartImages?.clearart?.firstOrNull()?.url
        val clearlogo = fanartImages?.clearlogo?.firstOrNull()?.url
        val hdclearart = fanartImages?.hdclearart?.firstOrNull()?.url
        val hdtvlogo = fanartImages?.hdtvlogo?.firstOrNull()?.url
        val seasonbanner = fanartImages?.seasonbanner?.firstOrNull()?.url
        val seasonposter = fanartImages?.seasonposter?.lastOrNull()?.url
        val seasonthumb = fanartImages?.seasonthumb?.lastOrNull()?.url
        val showbackground = fanartImages?.showbackground?.firstOrNull()?.url
        val tvbanner = fanartImages?.tvbanner?.firstOrNull()?.url
        val tvposter = fanartImages?.tvposter?.firstOrNull()?.url
        val tvthumb = fanartImages?.tvthumb?.firstOrNull()?.url

        val image16x9 = tvthumb ?: seasonthumb

        val other = tvposter ?: seasonposter ?: showbackground ?: tvbanner ?: seasonbanner

        return image16x9 ?: other
    }
}
