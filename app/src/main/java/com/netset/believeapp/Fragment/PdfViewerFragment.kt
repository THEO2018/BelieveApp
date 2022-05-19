package com.netset.believeapp.Fragment

import android.app.Activity
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.barteksc.pdfviewer.PDFView
import com.netset.believeapp.DownloadFileFromUrl
import com.netset.believeapp.R
import com.netset.believeapp.Utils.CommonDialogs
import com.netset.believeapp.activity.HomeActivity
import com.netset.believeapp.callbacks.CheckPermissionInterface
import kotlinx.android.synthetic.main.pdf_viewer_frag.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import java.util.*

class PdfViewerFragment(val url: String,val title:String) : BaseFragment(), CheckPermissionInterface {
    private var input: InputStream?=null
    private var dark: Boolean = true

    var pdfView:PDFView?=null
    var unbinder: Unbinder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.pdf_viewer_frag, null)
        unbinder = ButterKnife.bind(this, rootView)
        pdfView=rootView.findViewById(R.id.pdfView)
        (activity as HomeActivity?)!!.setToolbarTitle(title, false, false, false, null)
        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission=this
        btnNext.setOnClickListener {
            if (pdfView!!.currentPage+1!=pdfView!!.pageCount){
                pdfView!!.jumpTo(pdfView!!.currentPage+1);//this line helps to jump to any page of the pdf
                totalPages_current.text=(pdfView!!.currentPage+1).toString()+"/"+ (pdfView!!.pageCount).toString()
            }else{
                CommonDialogs.customToast(requireContext(),"You are on last page")
            }
        }
        downloadPdf.setOnClickListener {
            checkPermissionsForCamera()
        }
        btnPrev.setOnClickListener {
            if (pdfView!!.currentPage!=0){
                pdfView!!.jumpTo(pdfView!!.currentPage-1);//this line helps to jump to any page of the pdf
                totalPages_current.text=(pdfView!!.currentPage+1).toString()+"/"+ (pdfView!!.pageCount).toString()
            }else{
                CommonDialogs.customToast(requireContext(),"You are on first page")
            }
        }
        btnGo.setOnClickListener {
            if (gotoPages.text.isNullOrEmpty()){
                return@setOnClickListener
            }
            pdfView!!.jumpTo(gotoPages.text.toString().toInt()-1)
            totalPages_current.text=(pdfView!!.currentPage+1).toString()+"/"+ (pdfView!!.pageCount).toString()
            hideKeyboard(requireActivity())
        }
        loadPdf(false)
        darkMode.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            if (dark){
                loadPdf(true)
                darkMode.setColorFilter(context?.resources?.getColor(R.color.redish_pink)!!)
                dark=false
            }else{
                loadPdf(false)
                darkMode.setColorFilter(context?.resources?.getColor(R.color.black)!!)
                dark=true
            }

            }
        gotoPages.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode === KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                pdfView!!.jumpTo(gotoPages.text.toString().toInt() - 1)
                totalPages_current.text =
                    (pdfView!!.currentPage + 1).toString() + "/" + (pdfView!!.pageCount).toString()
            }
            false
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadPdf(enableDarkMode:Boolean){
        GlobalScope.launch{
                 input = URL(url).openStream()
            pdfView!!.fromStream(input)
                    .enableSwipe(true)
                    .swipeHorizontal(true)
                    .enableDoubletap(false)
                    .defaultPage(0)
                    .onDraw { canvas, pageWidth, pageHeight, displayedPage ->
                        if (enableDarkMode){
                            canvas.drawColor(Color.argb(0.4f,0f,0f,0f))
                        }else{
                            canvas.drawColor(Color.TRANSPARENT)

                        }
                    }
                    .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->
                    }
                    .onLoad {
                        progressBar.visibility=View.GONE

                    }
                    .onPageChange { page, pageCount ->
                        totalPages_current.text=(pdfView!!.currentPage+1).toString()+"/"+ (pdfView!!.pageCount).toString()
                        gotoPages.text.clear()
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

        totalPages_current.text=pdfView!!.currentPage.toString()+"/"+ pdfView!!.pageCount.toString()

    }
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun OnPermissionAccepted() {
        DownloadFileFromUrl(requireContext(), ".pdf").execute(url)
    }
}


