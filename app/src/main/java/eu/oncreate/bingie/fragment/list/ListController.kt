package eu.oncreate.bingie.fragment.list

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController

class ListController(private val context: Context, private val listener: (ShowWithImages, view: View) -> Unit) :
    TypedEpoxyController<List<ShowWithImages>>() {

    override fun buildModels(data: List<ShowWithImages>) {
        data.forEach {
            val show = it.searchResultItem.show
            itemSeries {
                id("view holder ${show.ids.trakt}")
                item(it)
                listener(listener)
            }
        }
    }

    override fun isDuplicateFilteringEnabled(): Boolean = true
}
