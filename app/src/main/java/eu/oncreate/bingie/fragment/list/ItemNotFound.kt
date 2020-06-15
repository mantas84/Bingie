package eu.oncreate.bingie.fragment.list

import android.widget.ImageView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import eu.oncreate.bingie.R
import eu.oncreate.bingie.utils.epoxy.KotlinEpoxyHolder

@EpoxyModelClass(layout = R.layout.item_not_found)
abstract class ItemNotFound : EpoxyModelWithHolder<ItemNotFound.Holder>() {

    class Holder : KotlinEpoxyHolder() {
        val image by bind<ImageView>(R.id.itemNotFound)
    }
}
