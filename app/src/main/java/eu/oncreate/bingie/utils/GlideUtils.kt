package eu.oncreate.bingie.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import eu.oncreate.bingie.R

object GlideUtils {

    fun ImageView.load(imageUrl: String) {
        GlideApp.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .fitCenter()
            .into(this)
    }

    // todo proper placeholder & errorDrawable
    fun ImageView.loadCenterCrop(
        imageUrl: String?,
        @DrawableRes placeHolder: Int = R.drawable.ic_launcher_foreground,
        @DrawableRes errorDrawable: Int = R.drawable.ic_launcher_foreground
    ) {
        GlideApp.with(this)
            .load(imageUrl)
            .placeholder(placeHolder)
            .error(errorDrawable)
            .centerCrop()
            .into(this)
    }
}
