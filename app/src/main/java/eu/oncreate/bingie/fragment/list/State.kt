package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.api.model.tmdb.Configuration

data class State(
    val query: String = "",
    val data: List<ShowWithImages> = emptyList(),
    val tmdbConfig: Async<Configuration> = Uninitialized,
    val searchResult: Async<PagedResponse<List<ShowWithImages>>> = Uninitialized,
    val page: Int = 1,
    val hasNext: Boolean = true
) : MvRxState {
    constructor(args: InitialListState) : this(query = args.searchQuery)
}
