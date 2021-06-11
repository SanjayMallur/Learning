package com.sanjay.learning.glide

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager

/**
 * A thin wrapper to hide Glide init and expose only Glide.RequestBuilder
 * to build the pipeline.
 */
class ImageLoader private constructor(
    val fragment: Fragment? = null,
    val activity: Activity? = null
) {
    companion object {
        fun with(fragment: Fragment) = ImageLoader(fragment = fragment)
        fun with(activity: Activity) = ImageLoader(activity = activity)
    }

    private val requestManager: RequestManager
        get() = when {
            fragment != null -> Glide.with(fragment)
            else -> Glide.with(activity!!)
        }

    /**
     * Use this function for any Image related usage to fetch images
     * from remote
     */
    fun load(url: String): RequestBuilder<Drawable> = with(requestManager) {
       if (url.isNotEmpty() || url.isNotBlank()){
           load(url)
       }else {
           load("")
       }
    }
}
