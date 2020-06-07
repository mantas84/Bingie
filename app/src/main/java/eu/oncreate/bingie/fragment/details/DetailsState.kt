package eu.oncreate.bingie.fragment.details

import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.PersistState
import eu.oncreate.bingie.fragment.list.ShowWithImages

data class DetailsState(@PersistState val item: ShowWithImages) : MvRxState {
    constructor(initState: InitialDetailsState) : this(item = initState.item)
}
