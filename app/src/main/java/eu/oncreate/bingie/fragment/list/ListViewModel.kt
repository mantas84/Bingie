package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.appendAt
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.store.StoreSource
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import eu.oncreate.bingie.utils.Constants
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.rx2.rxSingle
import java.util.concurrent.TimeUnit

class ListViewModel @AssistedInject constructor(
    @Assisted state: State,
    private val datasource: StoreSource
) : MvRxViewModel<State>(state) {

    private val disposable = CompositeDisposable()

    private val querySubject: PublishSubject<String> = PublishSubject.create()

    fun queryChanged(query: String) = setState {
        querySubject.onNext(query)
        copy(query = query)
    }

    init {
        observeChanges()
    }

    private fun observeChanges() = withState { state ->
        querySubject
            .startWith("")
            .distinctUntilChanged()
            .debounce(Constants.debounceTime, TimeUnit.MILLISECONDS)
            .doOnNext { setState { copy(searchResult = Loading()) } }
            .switchMapSingle { traktSearch(it) }
            .execute {
                when (it) {
                    is Success -> {
                        copy(
                            searchResult = it,
                            data = it.invoke()?.content.orEmpty(),
                            page = 1,
                            hasNext = it.invoke()?.hasNext ?: false
                        )
                    }
                    else -> copy(searchResult = it)
                }
            }
    }

    private fun traktSearch(query: String): Single<PagedResponse<List<ShowWithImages>>> {
        return rxSingle() {
            val searchResult = datasource.search(query, Constants.itemsPerPage, 1)
            swapContentInPage(datasource.getImages(searchResult.content.orEmpty()), searchResult)
        }
    }

    fun endReached() = withState { state ->
        if (state.hasNext && state.searchResult !is Loading) {
            rxSingle() {
                val searchResult =
                    datasource.search(state.query, Constants.itemsPerPage, state.page + 1)
                swapContentInPage(
                    datasource.getImages(searchResult.content.orEmpty()),
                    searchResult
                )
            }.execute {
                when (it) {
                    is Success -> {
                        copy(
                            searchResult = it,
                            data = data.appendAt(
                                other = it.invoke().content.orEmpty(),
                                offset = page.times(Constants.itemsPerPage)
                            ),
                            page = this.page + 1,
                            hasNext = it.invoke().hasNext
                        )
                    }
                    else -> copy(searchResult = it)
                }
            }
        }
    }

    private fun <T> swapContentInPage(
        content: T,
        other: PagedResponse<List<SearchResultItem>>
    ): PagedResponse<T> {
        return PagedResponse(
            content = content,
            page = other.page,
            limit = other.limit,
            totalPages = other.totalPages,
            totalItems = other.totalItems,
            error = other.error
        )
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

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}
