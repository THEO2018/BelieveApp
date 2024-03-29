package com.netset.believeapp.retrofitManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.activity.UserAuthenticationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import me.drakeet.materialdialog.MaterialDialog;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Neeraj Narwal on 31/5/17.
 */
public class ApiHitAndHandle implements Callback {

    static final String TAG = ApiHitAndHandle.class.getSimpleName();
    private static ApiHitAndHandle apiHitAndHandle;
    private static Context mContext;
    public static ProgressDialog pDialog;
    private BaseActivity baseActivity;
    private HashMap<Call, ApiResponse> apiResponseHashMap = new HashMap<>();

    public static ApiHitAndHandle getInstance(Context context) {
        if (apiHitAndHandle == null) {
            apiHitAndHandle = new ApiHitAndHandle();
        }
        mContext = context;
        return apiHitAndHandle;
    }

    public void makeApiCall(Call call, ApiResponse response, BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        makeApiCall(call, true, response);
    }

    public void makeApiCall(Call call, ApiResponse response, boolean isProgress, BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        makeApiCall(call, isProgress, response);
    }

    public void dismissDialog() {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog() {
        try {
            pDialog = new ProgressDialog(baseActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Loading..");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);
            pDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeApiCall(Call call, boolean showProgress, ApiResponse apiResponse) {
        ApiHitAndHandle classReference = this;
        new Handler().postDelayed(() -> {
            boolean isNetworkAvailable = checkConnection(baseActivity);
            if (isNetworkAvailable) {
                try {
                    apiResponseHashMap.put(call, apiResponse);
                    call.enqueue(classReference);

                    if (showProgress) {
                        dismissDialog();
                        pDialog = new ProgressDialog(baseActivity, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        pDialog.setMessage("Loading..");
                        pDialog.setCancelable(false);
                        pDialog.setIndeterminate(true);
                        pDialog.show();

                    }
                    //Logs post URL
                    log(call.request().url() + "");
                    //Logs post params of Multipart request
            /*log("Post Params >>>> \n" + bodyToString(call.request().body())
                    .replace("\r", "")
                    .replaceAll("--+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                    .replaceAll("Content+[a-zA-Z0-9-\\/:=;\\ ]+;\\s", "")
                    .replaceAll("Content+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                    .replaceAll("charset+[a-zA-Z0-9-\\/:=;\\ ]+\\n", "")
                    .replace("name=", "")
                    .replace("\n\n", "--> ")
            );*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonDialogs.ShowErrorDialog(baseActivity, "No Internet Available");
            }
        }, 1);
    }

    private void log(String s) {
        Log.e(TAG, s);

    }

    private String bodyToString(final RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        ApiResponse apiResponse = apiResponseHashMap.get(call);

        Log.e("response>>", response.code() + "");
        int responseCode = response.code();
        if (responseCode == 401) {
            try {
                try {
                    try {
                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    String message = jsonObject.optString("message");


                    final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
                    mMaterialDialog.setTitle("Alert");
                    mMaterialDialog.setMessage(message);
                    mMaterialDialog.setPositiveButton("OK", v -> {
                        mMaterialDialog.dismiss();

                        GeneralValues.set_loginbool(mContext, false);
                        GeneralValues.set_Access_Key(mContext, "");
                        mContext.startActivity(new Intent(mContext, UserAuthenticationActivity.class));
                        ((Activity) mContext).finish();

                    });

                    mMaterialDialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (responseCode == 402) {
            try {
                try {
                    try {
                        if (pDialog.isShowing()) {
                            pDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    String message = jsonObject.optString("message");


                    final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
                    mMaterialDialog.setTitle("Update");
                    mMaterialDialog.setMessage(message);
                    mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mMaterialDialog.dismiss();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store?hl=en"));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, "Play store not found", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                    mMaterialDialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (responseCode == 200) {
            try {
                pDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            apiResponse.onSuccess(call, response.body().toString());
        } else {
            try {
                pDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    String message = jsonObject.optString("message");
                    final MaterialDialog mMaterialDialog = new MaterialDialog(mContext);
                    mMaterialDialog.setTitle("Alert");
                    mMaterialDialog.setMessage(message);
                    mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mMaterialDialog.dismiss();

                        }
                    });

                    mMaterialDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        apiResponseHashMap.remove(call);
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("response>>", t.getMessage());
        Log.e("response>>", t.getLocalizedMessage());
        try {
            pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CommonDialogs.customToast(mContext, "Server not responding. Please try again.");

        ApiResponse apiResponse = apiResponseHashMap.get(call);
        apiResponse.onError(call, t.getMessage(), apiResponse);
        apiResponseHashMap.remove(call);
    }

    public Dialog oneButtonDialogone(Context c, String msg, String buttonTxt, DialogInterface.OnClickListener clickListener) {
        androidx.appcompat.app.AlertDialog.Builder dialog = new androidx.appcompat.app.AlertDialog.Builder(c);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setNeutralButton(buttonTxt, clickListener);
        return dialog.create();
    }

    private boolean checkConnection(Context context) {
        return baseActivity.isConnected(context);
    }


}
