package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.data.Datasource
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.rx2.rxSingle
import java.util.concurrent.TimeUnit

class ListViewModel @AssistedInject constructor(
    @Assisted state: State,
    private val datasource: Datasource
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
            .debounce(400, TimeUnit.MILLISECONDS)
            .doOnNext { setState { copy(searchResult = Loading()) } }
            .switchMapSingle { traktSearch(it, state) }
            .execute {
                when (it) {
                    is Success -> {
                        copy(searchResult = it, data = it.invoke().orEmpty())
                    }
                    else -> copy(searchResult = it)
                }
            }
    }

    private fun traktSearch(query: String, state: State): Single<List<ShowWithImages>> {
        return rxSingle() {
            val searchResult = datasource.search(query)
//        val config = state.tmdbConfig.invoke()
            val list = mutableListOf<ShowWithImages>()
            searchResult.forEach {
                val image = datasource.getImages(it)
                list.add(image)
            }
            list.toList()
        }
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
