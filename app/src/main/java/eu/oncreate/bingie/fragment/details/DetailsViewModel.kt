package eu.oncreate.bingie.fragment.details

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import eu.oncreate.bingie.api.TraktApi
import eu.oncreate.bingie.fragment.base.MvRxViewModel

class DetailsViewModel @AssistedInject constructor(
    @Assisted state: DetailsState,
    private val traktApi: TraktApi
) : MvRxViewModel<DetailsState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: DetailsState): DetailsViewModel
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
}
