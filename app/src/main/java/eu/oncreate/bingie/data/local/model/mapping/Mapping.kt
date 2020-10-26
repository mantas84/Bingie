package eu.oncreate.bingie.data.local.model.mapping

import eu.oncreate.bingie.data.api.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.api.model.trakt.SeasonsItem
import eu.oncreate.bingie.data.api.model.tmdb.Configuration
import eu.oncreate.bingie.data.api.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.tmdb.Backdrop
import eu.oncreate.bingie.data.local.model.tmdb.Poster
import eu.oncreate.bingie.data.local.model.trakt.Ids
import eu.oncreate.bingie.data.local.model.trakt.Show
import java.time.Instant
import eu.oncreate.bingie.data.local.model.tmdb.Configuration as LocalConfiguration
import eu.oncreate.bingie.data.local.model.tmdb.Images as LocalConfigurationImages
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages as LocalTmdbImages
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem as LocalSearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem as LocalSeasonsItem

fun getLocal(item: Configuration): LocalConfiguration {
    return LocalConfiguration(
        changeKeys = item.changeKeys,
        images = LocalConfigurationImages.toLocal(item.images),
        timestamp = Instant.now().epochSecond
    )
}

fun getLocal(item: TmdbImages): LocalTmdbImages {
    return LocalTmdbImages(
        item.id,
        item.backdrops.map { Backdrop.toLocal(it) },
        item.posters.map { Poster.toLocal(it, item.id) })
}

fun getLocal(item: SearchResultItem): LocalSearchResultItem {
    return LocalSearchResultItem(
        parentId = item.show.ids.trakt,
        score = item.score,
        show = Show.toLocal(item.show),
        type = item.type
    )
}

fun getLocal(item: SeasonsItem, seriesTraktId: Int): LocalSeasonsItem {
    return LocalSeasonsItem(
        airedEpisodes = item.airedEpisodes,
        episodeCount = item.episodeCount,
        firstAired = item.firstAired,
        ids = Ids.toLocal(item.ids),
        network = item.network,
        number = item.number,
        overview = item.overview,
        rating = item.rating,
        title = item.title,
        votes = item.votes,
        seriesTraktId = seriesTraktId
    )
}
