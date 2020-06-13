package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import eu.oncreate.bingie.api.model.tmdb.Configuration

data class State(
    val query: String = "",
    val data: List<ShowWithImages> = emptyList(),
    val tmdbConfig: Async<Configuration> = Uninitialized,
    val searchResult: Async<List<ShowWithImages>> = Uninitialized
) : MvRxState {
    constructor(args: InitialListState) : this(query = args.searchQuery)
}
