package com.netset.believeapp.Fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import butterknife.ButterKnife
import butterknife.Unbinder
import com.netset.believeapp.R
import com.netset.believeapp.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_pdf_view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL


class PdfViewFrag(val url: String) : BaseFragment(){
    var unbinder: Unbinder? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_pdf_view, null)
        unbinder = ButterKnife.bind(this, rootView)
        (activity as HomeActivity?)!!.setToolbarTitle("Recommendation Letter", false, false, false, null)
        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPdf()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPdf(){
        GlobalScope.launch {
            unbinder.apply {
                val input = URL(url).openStream()
                pdfViewRecommendation.fromStream(input)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(false)
                    .defaultPage(0)
                    .onDraw { canvas, pageWidth, pageHeight, displayedPage ->
                    }
                    .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->
                    }
                    .onLoad {
                        progressBar.visibility=View.GONE

                    }
                    .onPageChange { page, pageCount ->
                    }
                    .onPageScroll { page, positionOffset -> }
                    .onError {
                        Log.d("errorPdf",it.message.toString())
                    }
                    .onPageError { page, t ->
                        Log.d("errorPdf",t.message.toString())

                    }
                    .onRender { nbPages, pageWidth, pageHeight ->  }
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true)
                    .spacing(80)
                    .load()
            }
        }

    }

}