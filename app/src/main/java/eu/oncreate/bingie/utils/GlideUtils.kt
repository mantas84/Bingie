package eu.oncreate.bingie.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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
        onLoadingFinished: () -> Unit = {},
        @DrawableRes placeHolder: Int = R.drawable.ic_launcher_foreground,
        @DrawableRes errorDrawable: Int = R.drawable.ic_launcher_foreground
    ) {
        val listener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        }

        GlideApp.with(this)
            .load(imageUrl)
            .placeholder(placeHolder)
            .error(errorDrawable)
            .centerCrop()
            .listener(listener)
            .into(this)
    }
}
