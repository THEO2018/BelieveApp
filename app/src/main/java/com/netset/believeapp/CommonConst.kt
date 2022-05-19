package com.netset.believeapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import me.saket.bettermovementmethod.BetterLinkMovementMethod
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonConst {
    companion object{
        var fromJobDesc= false
        var positionMain=0
        fun loadGlide(context: Context, path: Any, placeHolder: Int?): RequestBuilder<Drawable> {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            return Glide.with(context).load(path)
                .centerCrop()
                .apply(
                    RequestOptions().error(
                        if (placeHolder != null) ContextCompat.getDrawable(
                            context,
                            placeHolder
                        ) else circularProgressDrawable
                    )
                        .placeholder(
                            if (placeHolder != null) ContextCompat.getDrawable(
                                context,
                                placeHolder
                            ) else circularProgressDrawable
                        )
                        .centerCrop()
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        }

        fun utcToLocalDate(dateNew: String): String {
            val df = SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            var date: Date? = null
            try {
                date =
                    df.parse(dateNew)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            df.timeZone = TimeZone.getDefault()
            return df.format(date)
        }

        fun loadGlideCircular(context: Context, path: Any, placeHolder: Int?): RequestBuilder<Drawable> {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            return Glide.with(context).load(path)
                .circleCrop()
                .apply(
                    RequestOptions().error(
                        if (placeHolder != null) ContextCompat.getDrawable(
                            context,
                            placeHolder
                        ) else circularProgressDrawable
                    )
                        .placeholder(
                            if (placeHolder != null) ContextCompat.getDrawable(
                                context,
                                placeHolder
                            ) else circularProgressDrawable
                        )
                        .centerCrop()
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        }
    }
}