package eu.oncreate.bingie.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import eu.oncreate.bingie.R
import eu.oncreate.bingie.fragment.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailsViewModel.Factory

    private val viewModel: DetailsViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun invalidate() = withState(viewModel) { state ->

        Timber.d("state $state")
    }
}
