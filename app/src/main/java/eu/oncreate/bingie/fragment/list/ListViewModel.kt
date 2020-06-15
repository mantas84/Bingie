package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.BuildConfig
import eu.oncreate.bingie.api.FanartApi
import eu.oncreate.bingie.api.TmdbApi
import eu.oncreate.bingie.api.TraktApi
import eu.oncreate.bingie.api.model.SearchResultItem
import eu.oncreate.bingie.api.model.fanart.FanartImages
import eu.oncreate.bingie.api.model.tmdb.Configuration
import eu.oncreate.bingie.api.model.tmdb.TmdbImages
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ListViewModel @AssistedInject constructor(
    @Assisted state: State,
    private val traktApi: TraktApi,
    private val tmdbApi: TmdbApi,
    private val fanartApi: FanartApi
) : MvRxViewModel<State>(state) {

    private val disposable = CompositeDisposable()

    private val querySubject: PublishSubject<String> = PublishSubject.create()

    fun queryChanged(query: String) = setState {
        querySubject.onNext(query)
        copy(query = query)
    }

    init {
        checkTmdbConfig()
        observeChanges()
    }

    private fun observeChanges() = withState { state ->
        querySubject
            .startWith("")
            .distinctUntilChanged()
            .debounce(400, TimeUnit.MILLISECONDS)
            .doOnNext { setState { copy(searchResult = Loading()) } }
            .switchMapSingle { traktSearch(it, state) }
            .execute {
                when (it) {
                    is Success -> { copy(searchResult = it, data = it.invoke().orEmpty()) }
                    else -> copy(searchResult = it)
                }
            }
    }

    private fun traktSearch(query: String, state: State): Single<List<ShowWithImages>> {
        return traktApi.search(query)
            .subscribeOn(Schedulers.io())
            .flatMap {
                val config = state.tmdbConfig.invoke()

                val pairs = Observable
                    .fromIterable(it)
                    .flatMapSingle { item -> getImages(item, config) }
                    .toList()
                    .map { it.toList() }
                pairs
            }
    }

    private fun getImages(
        item: SearchResultItem,
        configuration: Configuration?
    ): Single<ShowWithImages> {

        val tmdbFallback = TmdbImages(backdrops = emptyList(), id = -1, posters = emptyList())

        val fanartFallback = FanartImages(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val tmdb = when (val id = item.show.ids.tmdb) {
            null -> Single.just(tmdbFallback)
            else -> tmdbApi.getImages(id, BuildConfig.TMDB_TOKEN).onErrorReturnItem(tmdbFallback)
        }

        val fanart = when (val id = item.show.ids.tvdb) {
            null -> Single.just(fanartFallback)
            else -> fanartApi.getImages(id, BuildConfig.FANART_KEY)
                .onErrorReturnItem(fanartFallback)
        }

        return Single.zip(
            tmdb,
            fanart,
            BiFunction { tmdbImage: TmdbImages, fanartImage: FanartImages ->
                ShowWithImages(
                    item,
                    tmdbImage.takeUnless { it.id == -1 },
                    configuration?.images,
                    fanartImage.takeUnless { it.thetvdbId == null }
                )
            })
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: State): ListViewModel
    }

    companion object : MvRxViewModelFactory<ListViewModel, State> {
        override fun create(viewModelContext: ViewModelContext, state: State): ListViewModel? {
            val fragment =
                (viewModelContext as FragmentViewModelContext).fragment<ListFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    private fun checkTmdbConfig() {
        // todo do caching
        tmdbApi
            .getConfiguration(BuildConfig.TMDB_TOKEN)
            .subscribeOn(Schedulers.io())
            .execute { copy(tmdbConfig = it) }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}
