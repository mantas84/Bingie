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
import eu.oncreate.bingie.data.store.StoreSource
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import kotlinx.coroutines.rx2.rxSingle
import kotlin.math.min

class DetailsViewModel @AssistedInject constructor(
    @Assisted state: DetailsState,
    val datasource: StoreSource
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

    private fun getData(refresh: Boolean = false) = withState {
        rxSingle {
            val show = datasource.getShow(it.traktId, refresh)
            val images = datasource.getImages(show, refresh)
            val seasons = datasource.getSeasons(it.traktId, refresh)
            Pair(images, seasons)
        }.execute {
            when (it) {
                is Uninitialized -> copy()
                is Loading -> copy()
                is Success -> copy(
                    seasons = it.invoke().second,
                    item = it.invoke().first,
                    isRefreshing = false
                )
                // todo: fail state
                is Fail -> {
                    copy(isRefreshing = false)
                }
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
            is DetailsEvent.Refresh -> {
                getData()
                copy(isRefreshing = true)
            }
        }
    }
}
