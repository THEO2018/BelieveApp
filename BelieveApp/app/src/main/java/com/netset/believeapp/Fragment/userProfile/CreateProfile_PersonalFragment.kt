package com.netset.believeapp.Fragment.userProfile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import com.netset.believeapp.Fragment.BaseFragment
import com.netset.believeapp.R
import com.netset.believeapp.Utils.CommonDialogs
import com.netset.believeapp.Utils.Constants
import com.netset.believeapp.Utils.GeneralValues
import com.netset.believeapp.activity.BaseActivity.OnCreateProfileListener
import com.netset.believeapp.activity.UserAuthenticationActivity
import com.netset.believeapp.callbacks.CheckPermissionInterface
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.utils.DiskCacheUtils
import com.nostra13.universalimageloader.utils.MemoryCacheUtils
import com.squareup.picasso.Picasso
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.internal.utils.PathUtils
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.create_profile_view_personal.*
import java.io.File
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * Created by netset on 9/1/18.
 */
class CreateProfile_PersonalFragment : BaseFragment(), OnCreateProfileListener, View.OnClickListener, CheckPermissionInterface {

    var unbinder: Unbinder? = null
    var videoFile: File? = null
    var profileImage: File? = null
    var mSelected: List<Uri>? = null
    var datePickerDialog: DatePickerDialog? = null
    var selectedFilePathOptional: String? = ""
    lateinit var maritalArray: Array<String>
    lateinit var genderArray: Array<String>
    lateinit var statusArray: Array<String>

    var maritalStatusOptional = ""
    var gender = ""
    var status = ""
    var professionalOptional = ""
    var campusOptional = ""
    var dateOfBirth = ""
    private var rootView: View? = null

    private inner class EmojiExcludeFilter : InputFilter {
        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return ""
                }
                val checkMe = source[i].toString()
                val pattern = Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' ]*")
                val matcher = pattern.matcher(checkMe)
                val valid = matcher.matches()
                if (!valid) {
                    Log.d("", "invalid")
                    return ""
                }
            }
            return null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.create_profile_view_personal, null)
        }
        unbinder = ButterKnife.bind(this, rootView!!)
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as UserAuthenticationActivity?)!!.setToolbarTitle(Constants.SC_CREATE_PROFILE, false, true, this)
        (activity as UserAuthenticationActivity?)!!.actionText_TV.text = "Next"

        checkPermission = this
        profession_ET!!.filters = arrayOf(EmojiExcludeFilter())
        campus_ET!!.filters = arrayOf(EmojiExcludeFilter())

        if (GeneralValues.get_logintype(activity) == "F" || GeneralValues.get_logintype(activity) == "G") {
            if (Constants.FBImage !== "") {
                profileImage = File(Constants.FBImage)
                selectedFilePathOptional = "" + profileImage
                baseActivity?.loadImageFromDevice(baseActivity, profileImage, selectImage_IM)
            }
        } else {
            selectedFilePathOptional = ""
            profileImage = null
        }

        baseActivity?.RegisterCreateProfileListener(this)

        parent!!.setOnTouchListener { view, motionEvent ->
            try {
                CommonDialogs.hideSoftKeyboard(activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            false
        }

        selectImage_IM.setOnClickListener(this)
        dob_ET.setOnClickListener(this)
        status_ET.setOnClickListener(this)
        maritalStatus_ET.setOnClickListener(this)
        gender_ET.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.selectImage_IM -> checkPermissionsForCamera()
            R.id.dob_ET -> {
                val c = Calendar.getInstance()
                val mYear = c[Calendar.YEAR]
                val mMonth = c[Calendar.MONTH]
                val mDay = c[Calendar.DAY_OF_MONTH]
                c.add(Calendar.YEAR, -10) //Goes 10 Year Back in time ^^
                val upperLimit = c.timeInMillis
                datePickerDialog = DatePickerDialog(requireContext(), R.style.datepicker,
                        { view, year, monthOfYear, dayOfMonth ->
                            val temp_month = monthOfYear + 1
                            val temp_year = year
                            dob_ET!!.text = year.toString() + "-" + temp((monthOfYear + 1).toString()) + "-" + dayOfMonth
                        }, mYear, mMonth, mDay)
                datePickerDialog!!.datePicker.maxDate = upperLimit
                datePickerDialog!!.show()
            }
            R.id.maritalStatus_ET -> {
                maritalArray = resources.getStringArray(R.array.marital_status_array)
                baseActivity?.commonFunctions?.showDailog(baseActivity, maritalArray, maritalStatus_ET, null)
            }
            R.id.gender_ET -> {
                genderArray = resources.getStringArray(R.array.gender_array)
                baseActivity?.commonFunctions?.showDailog(baseActivity, genderArray, gender_ET, null)
            }
            R.id.status_ET -> {
                statusArray = resources.getStringArray(R.array.status_array)
                baseActivity?.commonFunctions?.showDailog(baseActivity, statusArray, status_ET, null)
            }
        }
    }

    private fun temp(month: String): String {
        var t = ""
        t = if (month.toInt() < 9) {
            "0$month"
        } else {
            month
        }
        return t
    }

    override fun onCreateProfileResponse() {
        professionalOptional = profession_ET!!.text.toString().trim { it <= ' ' }
        campusOptional = campus_ET!!.text.toString().trim { it <= ' ' }
        dateOfBirth = dob_ET!!.text.toString().trim { it <= ' ' }
        maritalStatusOptional = maritalStatus_ET!!.text.toString().trim { it <= ' ' }
        gender = gender_ET!!.text.toString().trim { it <= ' ' }
        status = status_ET!!.text.toString().trim { it <= ' ' }
        if (isValidText(gender) && isValidText(status) && isValidText(dateOfBirth)) {
            val bundle = Bundle()
            bundle.putString(Constants.K_PROFILE_IMAGE, selectedFilePathOptional)
            bundle.putString(Constants.K_MARITAL_STATUS, maritalStatusOptional)
            bundle.putString(Constants.K_GENDER, gender)
            bundle.putString(Constants.K_PROFESSION, professionalOptional)
            bundle.putString(Constants.K_STATUS, status)
            bundle.putString(Constants.K_CAMPUS, campusOptional)
            bundle.putString(Constants.K_DATE_OF_BIRTH, dateOfBirth)
            baseActivity?.navigateFragmentTransaction_ARG(R.id.authViewContainer, CreateProfile_AddressFragment(), bundle)
        } else {
            if (!isValidText(gender)) {
                showToast("Select Gender")
            } else if (!isValidText(status)) {
                showToast("Select Status")
            } else if (!isValidText(dateOfBirth)) {
                showToast("Select Date of Birth")
            }
        }
    }

    private fun selectMedia() {
        ImagePicker.with(requireActivity())
                .crop()
                .compress(1024) //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                        1080,
                        1080
                )  //Final image resolution will be less than 1080 x 1080(Optional)
                .start { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK) {
                        profileImage = ImagePicker.getFile(data)!!
                        selectedFilePathOptional=profileImage.toString()
                        baseActivity?.loadImageFromDevice(baseActivity, profileImage, selectImage_IM)
                    } else if (resultCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                                .show()
                    } else {
                        Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun OnPermissionAccepted() {
        selectMedia()
    }
}