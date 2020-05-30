package eu.oncreate.bingie.utils

import android.widget.ImageView
import eu.oncreate.bingie.R

object GlideUtils {

    fun ImageView.load(imageUrl: String) {
        GlideApp.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .fitCenter()
            .into(this)
    }
}
