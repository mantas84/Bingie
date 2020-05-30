package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.api.TraktApi
import eu.oncreate.bingie.fragment.base.MvRxViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class ListViewModel @AssistedInject constructor(
    @Assisted state: State,
    private val traktApi: TraktApi
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

    private fun observeChanges() {
        querySubject
            .distinctUntilChanged()
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe { fetchData(it) }
            .addTo(disposable)
    }

    private fun fetchData(query: String) = withState { state ->
        traktApi.search(query)
            .subscribeOn(Schedulers.io())
            .doOnError { Timber.d("Error here $it") }
            .execute {
                when (it) {
                    is Success -> copy(searchResult = it, data = it.invoke().orEmpty())
                    else -> copy(searchResult = it)
                }
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
