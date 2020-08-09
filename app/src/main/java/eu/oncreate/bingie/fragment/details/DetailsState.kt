package eu.oncreate.bingie.fragment.details

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import eu.oncreate.bingie.fragment.list.ShowWithImages
import eu.oncreate.bingie.utils.minutesToHours
import kotlin.math.min

data class DetailsState(
    @PersistState
    val traktId: Int,
    val item: ShowWithImages? = null,
    val seasons: List<SeasonsItem> = emptyList(),
    val startSeason: Int = 1,
    val endSeason: Int = 1,
    val startEpisode: Int = 1,
    val endEpisode: Int = 1
) : MvRxState {
    constructor(initState: InitialDetailsState) : this(traktId = initState.traktId)

    val totalSeasons = seasons.maxBy { it.number }?.number ?: 0

    val enableSliders: Boolean =
        seasons.count() != 0 && item?.searchResultItem?.show?.airedEpisodes ?: 0 != 0

    val seasonsStartMin = 1
    val seasonsStartMax = min(endSeason, totalSeasons.coerceAtLeast(1))

    val seasonsEndMin = min(startSeason, totalSeasons.coerceAtLeast(1))
    val seasonsEndMax = totalSeasons.coerceAtLeast(1)

    val episodeStartMin = 1
    val episodeStartMax = when (startSeason) {
        endSeason -> {
            min(seasons.find { it.number == startSeason }?.episodeCount ?: 1, endEpisode).coerceAtLeast(1)
        }
        else -> (seasons.find { it.number == startSeason }?.episodeCount ?: 1).coerceAtLeast(1)
    }

    val episodeEndMin = when (startSeason) {
        endSeason -> startEpisode
        else -> 1
    }

    val episodeEndMax = (seasons.find { it.number == endSeason }?.episodeCount ?: 1).coerceAtLeast(1)

    val startEp = seasons
        .filter { it.number < startSeason }
        .fold(0, { acc, seasonsItem -> acc + seasonsItem.airedEpisodes }) +
            min(seasons.find { it.number == startSeason }?.airedEpisodes ?: 0, startEpisode)

    val endEp = seasons
        .filter { it.number < endSeason }
        .fold(0, { acc, seasonsItem -> acc + seasonsItem.airedEpisodes }) +
            min(seasons.getOrNull(endSeason - 1)?.airedEpisodes ?: 0, endEpisode)

    val bingieTime = ((endEp - startEp + 1L) * (item?.searchResultItem?.show?.runtime ?: 0)).minutesToHours()
}
