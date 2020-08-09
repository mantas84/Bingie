package eu.oncreate.bingie.fragment.details

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.data.Datasource
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import eu.oncreate.bingie.fragment.list.ShowWithImages
import io.reactivex.functions.BiFunction
import timber.log.Timber
import kotlin.math.min

class DetailsViewModel @AssistedInject constructor(
    @Assisted state: DetailsState,
    val datasource: Datasource
) : MvRxViewModel<DetailsState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: DetailsState): DetailsViewModel
    }

    init {
        getData()
    }

    companion object : MvRxViewModelFactory<DetailsViewModel, DetailsState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: DetailsState
        ): DetailsViewModel {
            val fragment =
                (viewModelContext as FragmentViewModelContext).fragment<DetailsFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    private fun getData() = withState {
        datasource
            .getShow(it.traktId)
            .flatMap { item -> datasource.getImages(item) }
            .zipWith(
                datasource.getSeasons(it.traktId.toInt()),
                BiFunction { showWithImages: ShowWithImages, seasons: List<SeasonsItem> ->
                    Pair(showWithImages, seasons)
                })
            .execute {
                when (it) {
                    is Uninitialized -> copy()
                    is Loading -> copy()
                    is Success -> copy(seasons = it.invoke()?.second ?: emptyList(), item = it.invoke()?.first)
                    // todo: fail state
                    is Fail -> {
                        Timber.d("FAIL ${it.error}")
                        copy()}
                }
            }
    }

    fun handleEvent(event: DetailsEvent) = setState {

        when (event) {
            is DetailsEvent.StartSeasonChanged -> copy(
                startSeason = event.value,
                startEpisode = min(
                    this.startEpisode,
                    (this.seasons.getOrNull(event.value)?.episodeCount ?: 1).coerceAtLeast(1)
                ),
                endEpisode = min(
                    this.endEpisode,
                    (this.seasons.getOrNull(event.value)?.episodeCount ?: 1).coerceAtLeast(1)
                )
            )
            is DetailsEvent.EndSeasonChanged -> copy(
                endSeason = event.value,
                startEpisode = min(
                    this.startEpisode,
                    (this.seasons.getOrNull(event.value)?.episodeCount ?: 1).coerceAtLeast(1)
                ),
                endEpisode = min(
                    this.endEpisode,
                    (this.seasons.getOrNull(event.value)?.episodeCount ?: 1).coerceAtLeast(1)
                )
            )
            is DetailsEvent.StartEpisodeChanged -> copy(startEpisode = event.value)
            is DetailsEvent.EndEpisodeChanged -> copy(endEpisode = event.value)
        }
    }
}
