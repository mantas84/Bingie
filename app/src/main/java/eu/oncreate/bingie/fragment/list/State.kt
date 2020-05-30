package eu.oncreate.bingie.fragment.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import eu.oncreate.bingie.api.model.SearchResultItem

data class State(
    val query: String = "",
    val data: List<SearchResultItem> = emptyList(),
    val searchResult: Async<List<SearchResultItem>> = Uninitialized
) : MvRxState {
    constructor(args: InitialListState) : this(query = args.searchQuery)
}
