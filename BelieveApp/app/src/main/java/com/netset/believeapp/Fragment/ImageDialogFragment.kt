package com.netset.believeapp.Fragment

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.DialogFragment
import com.google.android.exoplayer2.ExoPlayer
import com.netset.believeapp.CommonConst
import com.netset.believeapp.R
import com.potyvideo.library.AndExoPlayerView


class ImageDialogFragment(val image:String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_image_dialog, container, false)
        isCancelable=true
        val tag= this.tag
        val img=v.findViewById<ImageView>(R.id.imgId)
//        val close=v.findViewById<ImageView>(R.id.closeDialog)
        val video=v.findViewById<AndExoPlayerView>(R.id.videoId)

        if (tag=="V"){
            img.visibility=View.GONE

            video.setSource(image)

        }
        else if (tag=="P"){
            video.visibility=View.GONE
            CommonConst.loadGlide(requireContext(),image,R.drawable.empty).into(img)
        }
//        close.setOnClickListener {
//            dismiss()
//        }


        return  v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)

    }
    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

}