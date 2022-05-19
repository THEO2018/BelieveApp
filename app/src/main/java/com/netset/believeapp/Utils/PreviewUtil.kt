package com.netset.believeapp.Utils

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.netset.believeapp.R
import com.netset.believeapp.activity.BaseActivity
import kotlinx.android.synthetic.main.logout_bottom_dialog.view.*
import org.jsoup.Jsoup

object PreviewUtil {
    private val AGENT = "Mozilla"
    private val REFERRER = "http://www.google.com"
    private val TIMEOUT = 10000
    private val DOC_SELECT_QUERY = "meta[property^=og:]"
    private val OPEN_GRAPH_KEY = "content"
    private val PROPERTY = "property"
    private val OG_IMAGE = "og:image"
    private val OG_DESCRIPTION = "og:description"
    private val OG_URL = "og:url"
    private val OG_TITLE = "og:title"
    private val OG_SITE_NAME = "og:site_name"
    private val OG_TYPE = "og:type"

    fun preview(url:String?){
        val openGraphResult = PreviewUrlData()

        try {
            val response = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent(AGENT)
                .referrer(REFERRER)
                .timeout(TIMEOUT)
                .followRedirects(true)
                .execute()

            val doc = response.parse()

            val ogTags = doc.select(DOC_SELECT_QUERY)
            when {
                ogTags.size > 0 ->
                    ogTags.forEachIndexed { index, _ ->
                        val tag = ogTags[index]
                        val text = tag.attr(PROPERTY)

                        when (text) {
                            OG_IMAGE -> {
                                openGraphResult.image = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_DESCRIPTION -> {
                                openGraphResult.description = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_URL -> {
                                openGraphResult.url = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_TITLE -> {
                                openGraphResult.title = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_SITE_NAME -> {
                                openGraphResult.siteName = (tag.attr(OPEN_GRAPH_KEY))
                            }
                            OG_TYPE -> {
                                openGraphResult.type = (tag.attr(OPEN_GRAPH_KEY))
                            }
                        }
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

     fun logout(activity:BaseActivity) {
        val dialog = BottomSheetDialog(activity, R.style.BottomSheetDialog_Rounded)
//        val v = LayoutInflater.inflate(R.layout.logout_bottom_dialog, null)
        val v = LayoutInflater.from(activity).inflate(R.layout.logout_bottom_dialog, null)
        v.noLogout.setOnClickListener {
            dialog.dismiss()
        }
        v.yesLogout.setOnClickListener {


        }
        dialog.setCancelable(true)
        dialog.setContentView(v)
        dialog.show()
    }
     fun addNotes(activity:BaseActivity) {

        val dialog2 = Dialog(activity)
        val v1 = activity.layoutInflater.inflate(R.layout.logout_bottom_dialog, null)
        val window = dialog2.window

        dialog2.setCancelable(false)
        dialog2.setContentView(v1)
        dialog2.show()
    }
}
