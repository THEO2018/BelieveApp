package com.netset.believeapp.Utils;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.netset.believeapp.Adapter.DialogListAdapter;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.BaseActivity;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by netset on 22/1/18.
 */

public class CommonFunctions {

    static CommonFunctions common_functions = null;
    ShareDialog shareDialog;

    /**
     * Singleton instamce of class ...
     *
     * @return
     */
    public static CommonFunctions getInstance() {
        if (common_functions == null) {
            return common_functions = new CommonFunctions();
        } else {
            return common_functions;
        }
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView, int columns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = listAdapter.getCount();
        int rows = 0;

        View listItem = listAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if (items > columns) {
            x = items / columns;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);

    }

    /**
     * Change Given Date To Other Format ...
     *
     * @param time
     * @return
     */
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public boolean isDateAfter(String endDate) {

        try {
            Calendar c = Calendar.getInstance();
            System.out.println("Current time => " + c.getTime());

            String myFormatString = "dd-MM-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);

            String currentDate = df.format(c.getTime());
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(currentDate);
            Log.e("CommonFucntions", "" + currentDate);
            Log.e("CommonFucntions", "" + endDate);
            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }

    public static final String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.getDefault());

    public Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    public String monthName(int month) {
        String strMonth = "";
        switch (month) {
            case 1:
                strMonth = "Jan";
                break;

            case 2:
                strMonth = "Feb";
                break;

            case 3:
                strMonth = "Mar";
                break;

            case 4:
                strMonth = "Apr";
                break;

            case 5:
                strMonth = "May";
                break;

            case 6:
                strMonth = "Jun";
                break;

            case 7:
                strMonth = "Jul";
                break;

            case 8:
                strMonth = "Aug";
                break;

            case 9:
                strMonth = "Sep";
                break;

            case 10:
                strMonth = "Oct";
                break;

            case 11:
                strMonth = "Nov";
                break;

            case 12:
                strMonth = "Dec";
                break;
        }

        return strMonth;
    }


    /**
     * Convert Given Date Stamp to Date ...
     *
     * @param timeStamp
     * @return
     */

    public String getDateFromStamp(long timeStamp) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "xx";
        }
    }


    /**
     * Method checks if the app_icon is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    /* Sharing Methods .........*/

    private void shareToSms(Context mContext, String data) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", data);
        sendIntent.setType("vnd.android-dir/mms-sms");
        mContext.startActivity(sendIntent);
    }

    private void shareToWhatsApp(Context mContext) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        try {
            mContext.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareToMail(Context mContext, String subject, String data) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, data);
        mContext.startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
    }

    public void sharePhotoToFacebook(BaseActivity baseActivity, Bitmap bitmap) {

        shareDialog = new ShareDialog(baseActivity);
        Bitmap image = BitmapFactory.decodeResource(baseActivity.getResources(), R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    public void shareLinkToFacebook(BaseActivity baseActivity, String link) {
        shareDialog = new ShareDialog(baseActivity);
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(link))
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }


    /*-------------------------------------------------------------------------------*/

    public String showDailog(Context mContext, final String[] list, final TextView textView, final EditText editText) {
        final String[] returnValue = {""};
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);

        ListView optionList_LV = dialog.findViewById(R.id.optionList_LV);
        ImageView cancel_IV = dialog.findViewById(R.id.cancel_IV);


        DialogListAdapter dialogListAdapter = new DialogListAdapter(mContext, list);
        optionList_LV.setAdapter(dialogListAdapter);
        dialog.show();

        optionList_LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();

                if (textView != null) {
                    textView.setText(list[position]);
                } else if (editText != null) {
                    editText.setText(list[position]);
                }
                returnValue[0] = list[position];
            }
        });

        cancel_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return returnValue[0];
    }



    public String loadJSONFromAsset(BaseActivity baseActivity, String fileName) {
        String json = null;
        try {
            InputStream is = baseActivity.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
