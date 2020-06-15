package eu.oncreate.bingie.fragment.list

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import eu.oncreate.bingie.R
import eu.oncreate.bingie.fragment.details.DetailsFragment.Companion.getTransitionNamePicture
import eu.oncreate.bingie.fragment.details.DetailsFragment.Companion.getTransitionNameRatingBar
import eu.oncreate.bingie.utils.GlideUtils.loadCenterCropRound
import eu.oncreate.bingie.utils.epoxy.KotlinEpoxyHolder
import eu.oncreate.bingie.utils.hoursString
import eu.oncreate.bingie.utils.hoursToDaysString
import eu.oncreate.bingie.utils.minutesToHours

@EpoxyModelClass(layout = R.layout.item_series)
abstract class ItemSeries : EpoxyModelWithHolder<ItemSeries.Holder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var listener: (ShowWithImages, View, View) -> Unit

    @EpoxyAttribute
    lateinit var item: ShowWithImages

    override fun bind(holder: Holder) {
        holder.apply {
            val show = item.searchResultItem.show

            poster.transitionName = getTransitionNamePicture(show)
            rating.transitionName = getTransitionNameRatingBar(show)
            itemHolder.setOnClickListener { listener(item, poster, rating) }

            title.text = show.title.orEmpty()
            poster.loadCenterCropRound(item.getImage(), 24)
            rating.isVisible = show.votes > 0
            rating.progress = show.rating.times(100f).toInt().div(10f)
            duration.text =
                getDuration(show.airedEpisodes * show.runtime.toLong(), duration.context)
        }
    }

    private fun getDuration(minutes: Long, context: Context): String {

        val durationHours = minutes.minutesToHours()

        return if (durationHours > 24) {
            context.getString(R.string.days, durationHours.hoursToDaysString())
        } else {
            context.getString(R.string.hours, durationHours.hoursString())
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val poster by bind<ImageView>(R.id.imageView)
        val rating by bind<DonutProgress>(R.id.seriesRating)
        val title by bind<TextView>(R.id.seriesTitle)
        val duration by bind<TextView>(R.id.seriesDuration)
        val itemHolder by bind<View>(R.id.listItemHolder)
    }
}
