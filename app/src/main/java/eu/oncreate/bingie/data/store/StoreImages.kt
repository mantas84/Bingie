package eu.oncreate.bingie.data.store

import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import eu.oncreate.bingie.data.local.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.tmdb.Images
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.fragment.list.ShowWithImages
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import timber.log.Timber

class StoreImages(
    private val tmdbImages: Store<Int, TmdbImages>,
    private val fanart: Store<Int, FanartImages>
) {

    suspend fun getImages(
        items: List<SearchResultItem>,
        forceUpdate: Boolean = false,
        imagesConf: Images
    ): List<ShowWithImages> {

        val tmdbImages =
            getTmdbImages(items.map { it.show.ids.tmdb ?: -1 }, forceUpdate)
        val fanartImages =
            getFanartImages(items.map { it.show.ids.tvdb ?: -1 }, forceUpdate)

        return items.map { item ->
            ShowWithImages(
                item,
                tmdbImages
                    .find { item.show.ids.tmdb == it.first }?.second
                    ?: TmdbImages.EMPTY,
                imagesConf,
                fanartImages.find { item.show.ids.tvdb == it.first }?.second
                    ?: FanartImages.EMPTY
            )
        }
    }

    suspend fun getImages(
        item: SearchResultItem,
        forceUpdate: Boolean = false,
        imagesConf: Images
    ): ShowWithImages {

        val tmdbImages = getTmdbImage(item.show.ids.tmdb, forceUpdate)
        val fanartImages = getFanartImage(item.show.ids.tvdb, forceUpdate)

        return ShowWithImages(
            item,
            tmdbImages.takeUnless { it == TmdbImages.EMPTY },
            imagesConf,
            fanartImages.takeUnless { it == FanartImages.EMPTY })
    }

    private suspend fun getTmdbImages(
        itemIds: List<Int>,
        fresh: Boolean = false
    ): List<Pair<Int, TmdbImages>> = itemIds
        .asFlow()
        .flatMapMerge() { itemId ->
            flow { emit(itemId to getTmdbImage(itemId, fresh)) }
        }.toList()

    private suspend fun getFanartImages(
        tvdbIds: List<Int>,
        fresh: Boolean = false
    ): List<Pair<Int, FanartImages>> = tvdbIds
        .asFlow()
        .flatMapMerge() { tvdbId ->
            flow { emit(tvdbId to getFanartImage(tvdbId, fresh)) }
        }.toList()

    private suspend fun getTmdbImage(id: Int?, fresh: Boolean): TmdbImages {
        val empty = TmdbImages.EMPTY
        return if (id == null) {
            empty
        } else {
            try {
                if (fresh) {
                    tmdbImages.fresh(id)
                } else {
                    tmdbImages.get(id)
                }
            } catch (e: Exception) {
                Timber.d("exception $e")
                empty
            }
        }
    }

    private suspend fun getFanartImage(id: Int?, fresh: Boolean): FanartImages {
        val empty = FanartImages.EMPTY
        return if (id == null) {
            empty
        } else {
            try {
                if (fresh) {
                    fanart.fresh(id)
                } else {
                    fanart.get(id)
                }
            } catch (e: Exception) {
                Timber.d("exception $e")
                empty
            }
        }
    }
}
