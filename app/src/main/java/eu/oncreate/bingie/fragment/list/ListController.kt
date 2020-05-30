package eu.oncreate.bingie.fragment.list

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import eu.oncreate.bingie.api.model.SearchResultItem

class ListController(private val context: Context, private val listener: (SearchResultItem) -> Unit) :
    TypedEpoxyController<List<SearchResultItem>>() {

    override fun buildModels(data: List<SearchResultItem>) {
        data.forEach {
            val show = it.show
            itemSeries {
                id("view holder ${show.ids.trakt}")
                item(it)
                listener(listener)
            }
        }
    }

    override fun isDuplicateFilteringEnabled(): Boolean = true
}
