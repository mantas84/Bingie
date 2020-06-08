package eu.oncreate.bingie.fragment.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import eu.oncreate.bingie.R
import eu.oncreate.bingie.fragment.base.BaseFragment
import eu.oncreate.bingie.utils.GlideUtils.loadCenterCrop
import kotlinx.android.synthetic.main.fragment_details.*
import timber.log.Timber
import javax.inject.Inject

class DetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: DetailsViewModel.Factory

    private val viewModel: DetailsViewModel by fragmentViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun invalidate() = withState(viewModel) { state ->

        state.item.apply {
            val show = searchResultItem.show
            detailsHeader.loadCenterCrop(getImage(), { startPostponedEnterTransition() })
            detailsTitle.text = show.title
            detailsDescription.text = show.overview

            detailsHeader.transitionName = show.ids.trakt.toString()
            detailsRating.text = show.rating.times(10f).toFloat().toString()
            detailsRuntime.text = show.runtime.toString()
            detailsStatus.text = show.status
            detailsEpisodes.text = show.airedEpisodes.toString()
        }

        Timber.d("state $state")
    }
}
