package eu.oncreate.bingie.fragment.details

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import eu.oncreate.bingie.api.model.SearchResultItem

data class DetailsState(@PersistState val item: SearchResultItem) : MvRxState {
    constructor(initState: InitialDetailsState) : this(item = initState.item)
}
