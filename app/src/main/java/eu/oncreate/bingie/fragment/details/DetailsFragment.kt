package eu.oncreate.bingie.fragment.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.robinhood.ticker.TickerUtils
import eu.oncreate.bingie.R
import eu.oncreate.bingie.api.model.Show
import eu.oncreate.bingie.fragment.base.BaseFragment
import eu.oncreate.bingie.utils.GlideUtils.loadCenterCrop
import eu.oncreate.bingie.utils.hoursString
import eu.oncreate.bingie.utils.hoursToDaysString
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import kotlinx.android.synthetic.main.fragment_details.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsTicker.setCharacterLists(TickerUtils.provideNumberList())
        initListeners()
    }

    private fun initListeners() {
        detailsSeasonsStartSlider.doOnProgressChanged { numberPicker, progress, formUser ->
            viewModel.handleEvent(DetailsEvent.StartSeasonChanged(progress))
        }
        detailsSeasonsEndSlider.doOnProgressChanged { numberPicker, progress, formUser ->
            viewModel.handleEvent(DetailsEvent.EndSeasonChanged(progress))
        }
        detailsEpisodeStartSlider.doOnProgressChanged { numberPicker, progress, formUser ->
            viewModel.handleEvent(DetailsEvent.StartEpisodeChanged(progress))
        }
        detailsEpisodeEndSlider.doOnProgressChanged { numberPicker, progress, formUser ->
            viewModel.handleEvent(DetailsEvent.EndEpisodeChanged(progress))
        }
    }

    override fun invalidate() = withState(viewModel) { state ->

        state.item.apply {
            val show = searchResultItem.show
            detailsHeader.loadCenterCrop(getImage(), { startPostponedEnterTransition() })
            detailsTitle.text = show.title
            detailsDescription.text = show.overview

            detailsHeader.transitionName = show.ids.trakt.toString()
            detailsSeriesRating.transitionName = "${show.ids.trakt}_rating"
            detailsSeriesRating.isVisible = show.votes > 0
            detailsSeriesRating.progress = show.rating.times(100f).toInt().div(10f)
            detailsSeasons.text = if (state.seasons.isNotEmpty()) {
                state.totalSeasons.toString()
            } else {
                ""
            }
            detailsRuntime.text = show.runtime.toString()
            detailsStatus.text = show.status
            detailsEpisodes.text = show.airedEpisodes.toString()

            updateSliders(state)
        }
        return@withState
    }

    private fun updateSliders(state: DetailsState) {

        enableSlider(state.enableSliders)

        detailsSeasonsStartSlider.minValue = state.seasonsStartMin
        detailsSeasonsStartSlider.maxValue = state.seasonsStartMax

        detailsSeasonsEndSlider.minValue = state.seasonsEndMin
        detailsSeasonsEndSlider.maxValue = state.seasonsEndMax

        detailsEpisodeStartSlider.minValue = state.episodeStartMin
        detailsEpisodeStartSlider.maxValue = state.episodeStartMax

        detailsEpisodeEndSlider.minValue = state.episodeEndMin
        detailsEpisodeEndSlider.maxValue = state.episodeEndMax

        // todo: field update does not shown until touched
        detailsEpisodeStartSlider.progress = state.startEpisode
        detailsEpisodeEndSlider.progress = state.endEpisode

        val timePair = if (state.bingieTime > 24) {
            Pair(getString(R.string.daysOnly), state.bingieTime.hoursToDaysString())
        } else {
            Pair(getString(R.string.hoursOnly), state.bingieTime.hoursString())
        }

        detailsTicker.text = timePair.second
        detailsTickerLabel.text = timePair.first
    }

    private fun enableSlider(value: Boolean) {
        detailsSeasonsStartSlider.isEnabled = value
        detailsSeasonsEndSlider.isEnabled = value
        detailsEpisodeStartSlider.isEnabled = value
        detailsEpisodeEndSlider.isEnabled = value
    }

    companion object {
        fun getTransitionNamePicture(show: Show) = show.ids.trakt.toString()
        fun getTransitionNameRatingBar(show: Show) = "${show.ids.trakt}_rating"
    }
}
