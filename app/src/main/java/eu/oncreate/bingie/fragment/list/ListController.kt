package eu.oncreate.bingie.fragment.list

import android.view.View
import com.airbnb.epoxy.TypedEpoxyController

class ListController(private val listener: (ShowWithImages, view: View, view2: View) -> Unit) :
    TypedEpoxyController<Pair<List<ShowWithImages>, Boolean>>() {

    override fun buildModels(pair: Pair<List<ShowWithImages>, Boolean>) {
        val data = pair.first
        val loading = pair.second
        if (data.isEmpty() && !loading) {
            itemNotFound { id(-1) }
        } else {
            data.forEach {
                val show = it.searchResultItem.show
                itemSeries {
                    id("view holder ${show.ids.trakt}")
                    item(it)
                    listener(listener)
                }
            }
        }
    }

    override fun isDuplicateFilteringEnabled(): Boolean = true
}
