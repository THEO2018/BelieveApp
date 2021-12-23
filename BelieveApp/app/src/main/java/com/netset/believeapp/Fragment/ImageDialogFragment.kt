package com.netset.believeapp.Fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.netset.believeapp.CommonConst
import com.netset.believeapp.R


class ImageDialogFragment(val image:String) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v= inflater.inflate(R.layout.fragment_image_dialog, container, false)
        isCancelable=true

        val img=v.findViewById<ImageView>(R.id.imgId)

        CommonConst.loadGlide(requireContext(),image,R.drawable.empty).into(img)

        return  v
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