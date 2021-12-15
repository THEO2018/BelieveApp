package com.netset.believeapp.Fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.netset.believeapp.activity.BaseActivity
import com.netset.believeapp.callbacks.CheckPermissionInterface
import java.util.regex.Pattern

/**
 * Created by netset on 8/1/18.
 */
open class BaseFragment : Fragment() {
    @JvmField
    var baseActivity: BaseActivity? = null
    lateinit var checkPermission: CheckPermissionInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity = activity as BaseActivity?
    }

    fun showLog(TAG: String?, message: String?) {
        baseActivity!!.showLog(TAG, message)
    }

    fun showToast(message: String) {
        Toast.makeText(baseActivity, "" + message, Toast.LENGTH_SHORT).show()
    }

    fun isValidText(text: String?): Boolean {
        return baseActivity!!.isValidText(text)
    }

    fun isValidEmail(email: String?): Boolean {
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
    //basefragment

    fun checkValidation(email: String?): Boolean {
        val input: String = email.toString()
        return if (input.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(input).matches()
        } else {
            Patterns.PHONE.matcher(input).matches()
        }
    }

    fun isValidMobile(phone: String?): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    fun checkPermissionsForCamera() {
        Dexter.withContext(baseActivity)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted()) {
                            checkPermission.OnPermissionAccepted()
                        }

                        if (report.isAnyPermissionPermanentlyDenied) {
                            openSettings()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: List<PermissionRequest>,
                            token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                }).withErrorListener { Log.e("Please check", "error") }.check()
    }
    fun checkPermissionsForLocation() {
        Dexter.withContext(baseActivity)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        checkPermission.OnPermissionAccepted()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                        openSettings()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { Log.e("Please check", "error") }.check()
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", baseActivity?.packageName, null)
        intent.data = uri
        startActivityForResult(intent, 1010)
    }

}