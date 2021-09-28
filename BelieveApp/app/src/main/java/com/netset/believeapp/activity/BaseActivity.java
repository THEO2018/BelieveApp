package com.netset.believeapp.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonFunctions;
import com.netset.believeapp.callbacks.SelectImageCallback;
import com.netset.believeapp.retrofitManager.ApiClient;
import com.netset.believeapp.retrofitManager.ApiHitAndHandle;
import com.netset.believeapp.retrofitManager.ApiInterface;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by netset on 8/1/18.
 */

public class BaseActivity extends AppCompatActivity {

    String BASE_TAG = BaseActivity.class.getSimpleName();
    public View divView;
    public ApiHitAndHandle apiHitAndHandle;
    public ApiInterface apiInterface;
    AlertDialog alert11;
    String[] permissionsRequired = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    private SharedPreferences permissionStatus;
    String userChoosenTask;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    PermCallback permCallback;
    private int reqCode;

    DatePickerDialog datePickerDialog = null;

    public CommonFunctions commonFunctions;
    public ApiClient apiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        generateHashKey();
        commonFunctions = CommonFunctions.getInstance();
        apiClient = new ApiClient(getApplicationContext());
        apiHitAndHandle = ApiHitAndHandle.getInstance(this);
        apiInterface = apiClient.getClient().create(ApiInterface.class);
    }


    /**
     * Method for generate Debug Hashkey ...
     */
    public void generateHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:>>>>>>>>>>>>>>>", "" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private int getStatusBarHeight() {
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentViewTop - statusBarHeight;

        return statusBarHeight;
    }

    public void showLog(String TAG, String message) {
        Log.e(TAG, message);
    }

    public void navigateFragmentTransaction(int containerID, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, fragment, "")
                .addToBackStack(null)
                .commit();
    }

    public void navigateFragmentTransaction_ARG(int containerID, Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, fragment, "")
                .addToBackStack(null)
                .commit();
    }

    public void navigateAddFragmentTransaction(int containerID, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerID, fragment, "")
                .addToBackStack(null)
                .commit();
    }

    public void navigateFragment_NoBackStack(int containerID, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, fragment, "")
                .commit();
    }


    public void navigateFragmentNoBackStack_ARG(int containerID, Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(containerID, fragment, "")
                .commit();
    }


    OnCreateProfileListener onCreateProfileListener;
    OnConfirmationDialogClickListener onConfirmationDialogClickListener;


    public interface OnCreateProfileListener {
        void onCreateProfileResponse();
    }

    public interface OnConfirmationDialogClickListener {
        void onConfirmationDialogClick(boolean isLogout);
    }

    public void RegisterCreateProfileListener(OnCreateProfileListener onCreateProfileListener) {
        this.onCreateProfileListener = onCreateProfileListener;
    }

    public void RegisterConfirmationDialogListener(OnConfirmationDialogClickListener onConfirmationDialogClickListener) {
        this.onConfirmationDialogClickListener = onConfirmationDialogClickListener;
    }


    public void confirmationAlertDialog(String message, final OnConfirmationDialogClickListener onConfirmationDialogClickListener,
                                        final boolean isLogout) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.alerrt_dialog_view, null);
        builder1.setView(view);
        builder1.setCancelable(true);

        alert11 = builder1.create();
        TextView message_TV = view.findViewById(R.id.message_TV);
        Button yes_TV = view.findViewById(R.id.yes_TV);
        Button no_TV = view.findViewById(R.id.no_TV);
        message_TV.setText(message);

        yes_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmationDialogClickListener.onConfirmationDialogClick(isLogout);
                alert11.cancel();
            }
        });

        no_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert11.cancel();
            }
        });

        alert11.show();
    }


    public boolean isValidText(String text) {
        if (text != null && !text.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void showToast(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to show all runtime permissions on app_icon startup ...
     *
     * @return
     */

    public void loadImageFromDevice(Context context, File path, ImageView imageView) {
        Glide.with(context).load(Uri.fromFile(path)).centerCrop().into(imageView);
    }

    public boolean runtimePermissions() {

        if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[3]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[4]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[5]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[6]) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[4])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[5])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[6])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app_icon needs Camera , Location ,Storage and Network permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(BaseActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

                /*SharedPreferences.Editor editor = permissionStatus.edit();
                editor.putBoolean(permissionsRequired[0],true);
                editor.commit();*/


            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app_icon needs permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
//                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Camera and Location", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

            }
        }
        return true;
    }


    SelectImageCallback selectImageCallback;

    /**
     * Method - Image Selection and click ...
     */
    public void selectImage(SelectImageCallback selectImageCallback) {
        this.selectImageCallback = selectImageCallback;
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";

                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";

                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * Go to Gallery ...
     */
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    /**
     * Go to Camera ...
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean permGrantedBool = false;
        switch (requestCode) {
            case 99:
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        showToast(getString(R.string.not_sufficient_permissions));
                        permGrantedBool = false;
                        break;
                    } else {
                        permGrantedBool = true;
                    }
                }
                if (permCallback != null) {
                    if (permGrantedBool) {
                        permCallback.permGranted(reqCode);
                    } else {
                        permCallback.permDenied(reqCode);
                    }
                }
                break;
        }
    }


    /**
     * Method to check runtime permissions
     *
     * @param perms
     * @param requestCode
     * @param permCallback
     * @return
     */

    public boolean checkPermissions(String[] perms, int requestCode, PermCallback permCallback) {
        this.permCallback = permCallback;
        this.reqCode = requestCode;
        ArrayList permsArray = new ArrayList<>();
        boolean hasPerms = true;
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                permsArray.add(perm);
                hasPerms = false;
            }
        }
        if (!hasPerms) {
            String[] permsString = new String[permsArray.size()];
            for (int i = 0; i < permsArray.size(); i++) {
                permsString[i] = (String) permsArray.get(i);
            }
            ActivityCompat.requestPermissions(BaseActivity.this, permsString, 99);
            return false;
        } else return true;
    }


    /**
     * Date Picker method ...
     *
     * @param text_view
     * @param edtText
     */
    public void datePicker(final TextView text_view, final TextView edtText, final boolean isDob) {
        final String[] date = {""};
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        datePickerDialog = new DatePickerDialog(this, R.style.datepicker,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        int temp_month = monthOfYear + 1;
                        int temp_year = year;

                        date[0] = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("dateIs", "" + date[0]);

                        if (!isDob) {
                            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        } else {
                            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                        }

                        Calendar c = Calendar.getInstance();
                        System.out.println("Current time => " + c.getTime());

                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String currentDate = df.format(c.getTime());
                        Date cDate = null;
                        try {
                            cDate = df.parse(currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // convert date to time stamp ...
                        Date date_s = null;
                        Date date_selected = null;
                        try {
                            date_s = (Date) df.parse(currentDate);
                            date_selected = (Date) df.parse(date[0]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Today is " + date_s.getTime());
                        long l_diff = date_selected.getTime() - date_s.getTime();

                        if (!isDob) {
                            //condition ...
                            Log.e(">>>>>>>>>>>>", "" + date_selected.getDate() + "diff" + ">>>>>>" + date_s);
                            if (l_diff < -1) {

                                int currentYear = c.get(Calendar.YEAR);
                                int currentMonth = c.get(Calendar.MONTH);

                                Log.e(BASE_TAG, "selected_date : " + date[0]);

                                if (temp_year == currentYear) {
                                    if (temp_month > currentMonth) {
                                        if (temp_month - currentMonth < 2) {
                                            if (text_view != null) {
                                                text_view.setText(date[0]);
                                            } else if (edtText != null) {
                                                edtText.setText(date[0]);
                                            }
                                        } else {
                                            showToast("Invalid");
                                        }
                                    } else if (temp_month < currentMonth) {
                                        if (currentMonth - temp_month < 2) {
                                            if (text_view != null) {
                                                text_view.setText(date[0]);
                                            } else if (edtText != null) {
                                                edtText.setText(date[0]);
                                            }
                                        } else {
                                            showToast("Invalid");
                                        }
                                    }
                                } else if (temp_year > currentYear) {
                                    if (temp_month < 2) {
                                        if (text_view != null) {
                                            text_view.setText(date[0]);
                                        } else if (edtText != null) {
                                            edtText.setText(date[0]);
                                        }
                                    } else {
                                        showToast("Invalid");
                                    }
                                }

                            } else {
                                showToast("Selected Date cannot be less than current date.");
                            }
                        } else {
                            if (!date_selected.after(date_s)) {
                                if (text_view != null) {
                                    text_view.setText(date[0]);
                                } else if (edtText != null) {
                                    edtText.setText(date[0]);
                                }
                            } else {
                                showToast("Date of birth cannot be future date.");
                            }
                        }
                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();

    }

    /**
     * Time Picker Method ...
     *
     * @param text_view
     * @param edtText
     * @return
     */
    public void timePicker(final TextView text_view, final EditText edtText, final String date, final String startTime) {
        final String[] time = {""};
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time[0] = selectedHour + ":" + selectedMinute;

                Log.e(BASE_TAG, "time : " + time[0]);
                if (!commonFunctions.isDateAfter(date)) {

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String currentDateandTime = sdf.format(new Date());
                    boolean str = commonFunctions.parseDate(time[0]).after(commonFunctions.parseDate(currentDateandTime));

                    if (str) {
                        if (startTime == null || startTime.equals("")) {
                            if (text_view != null) {
                                text_view.setText(time[0]);
                            } else if (edtText != null) {
                                edtText.setText(time[0]);
                            }
                        } else {
                            boolean str_time = commonFunctions.parseDate(time[0]).after(commonFunctions.parseDate(startTime));

                            if (str_time) {
                                if (text_view != null) {
                                    text_view.setText(time[0]);
                                } else if (edtText != null) {
                                    edtText.setText(time[0]);
                                }
                            } else {
                                showToast("Selected Time cannot be less than start time.");
                            }
                        }
                    } else {
                        showToast("Selected Time cannot be less than current time.");
                    }
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String currentDateandTime = sdf.format(new Date());
                    boolean str = commonFunctions.parseDate(time[0]).after(commonFunctions.parseDate(currentDateandTime));
                    if (commonFunctions.isDateAfter(date)) {
                        if (startTime == null || startTime.equals("")) {
                            if (text_view != null) {
                                text_view.setText(time[0]);
                            } else if (edtText != null) {
                                edtText.setText(time[0]);
                            }
                        } else {

                            boolean str_time = commonFunctions.parseDate(time[0]).after(commonFunctions.parseDate(startTime));
                            if (str_time) {
                                if (text_view != null) {
                                    text_view.setText(time[0]);
                                } else if (edtText != null) {
                                    edtText.setText(time[0]);
                                }
                            } else {
                                showToast("Selected Time cannot be less than start time.");
                            }
                        }
                    } else {
                        if (str) {
                            Log.e("MYTIME", "sdfsd : " + startTime);
                            if (startTime == null || startTime.equals("")) {
                                if (text_view != null) {
                                    text_view.setText(time[0]);
                                } else if (edtText != null) {
                                    edtText.setText(time[0]);
                                }
                            } else {

                                boolean str_time = commonFunctions.parseDate(time[0]).after(commonFunctions.parseDate(startTime));
                                if (str_time) {
                                    if (text_view != null) {
                                        text_view.setText(time[0]);
                                    } else if (edtText != null) {
                                        edtText.setText(time[0]);
                                    }
                                } else {
                                    showToast("Selected Time cannot be less than start time.");
                                }
                            }
                        } else {
                            showToast("Selected Time cannot be less than current time.");
                        }
                    }
                }
            }
        }, hour, minute, false);//Yes 24 hour time
        /* mTimePicker.setTitle("Select Time");*/
        mTimePicker.show();

    }


    /**
     * Interface ...
     */
    public interface PermCallback {
        void permGranted(int resultCode);

        void permDenied(int resultCode);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        *//*if (requestCode == SELECT_FILE || requestCode == REQUEST_CAMERA) {
            selectImageCallback.onSelectImage(requestCode, resultCode, data);
        }*//*
    }*/

    public OnActivityCallBack onActivityCallback;

    public void registerActvityResultCallback(OnActivityCallBack onActivityCallback) {
        this.onActivityCallback = onActivityCallback;
    }

    public interface OnActivityCallBack {
        void onResult(int requestCode, int resultCode, Intent data);
    }

}
