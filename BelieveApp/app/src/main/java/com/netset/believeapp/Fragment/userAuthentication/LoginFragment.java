package com.netset.believeapp.Fragment.userAuthentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.userProfile.CreateProfile_PersonalFragment;
import com.netset.believeapp.Model.BaseModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.Constants;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.UserAuthenticationActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.google.android.gms.internal.zzahf.runOnUiThread;
import static com.netset.believeapp.Utils.Constants.SC_LOGIN;

/**
 * Created by netset on 8/1/18.
 */

public class LoginFragment extends BaseFragment implements ApiResponse,GoogleApiClient.ConnectionCallbacks {

    @BindView(R.id.emailPhone_ET)
    AppCompatEditText emailPhoneET;
    @BindView(R.id.password_ET)
    AppCompatEditText passwordET;
    @BindView(R.id.forgotPassword_TV)
    AppCompatTextView forgotPasswordTV;
    @BindView(R.id.login_button)
    LoginButton loginBT;
    @BindView(R.id.login_BT)
    AppCompatButton simpleloginBt;
    @BindView(R.id.textView2)
    AppCompatTextView textView2;
    @BindView(R.id.fbLogin_BT)
    AppCompatButton fbLoginBT;
    @BindView(R.id.googleLogin_BT)
    AppCompatButton googleLoginBT;
    Unbinder unbinder;
    @BindView(R.id.parent)
    ScrollView parent;
    @BindView(R.id.main_lay)
    ConstraintLayout mainlay;
    TwitterAuthConfig authConfig;

    String timeZoneCurrent;
   /* ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> Login;
    public static  String emailtext,firstname,lastname,birthdaytext,id,name,url="",imageurl="";
    Call<JsonObject> signUp,signUp2;
    String first_name,last_name,access_token,user_id,profile_status;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Bitmap image;
    ShareDialog shareDialog;
    String socialLoginType = "";
    CallbackManager callbackManager;
    HashMap<String, String> map = new HashMap<String, String>();

    SharedPreferences.Editor mEdit;
    SharedPreferences mPreferencees;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((UserAuthenticationActivity) getActivity()).setToolbarTitle(SC_LOGIN, true, false, null);

      //  SocialLogin.getInstance().setListener(this);
        mPreferencees = getActivity().getSharedPreferences("Believe", getActivity().MODE_MULTI_PROCESS);
        mEdit = mPreferencees.edit();
        Constants.FBImage ="";
        callbackManager = CallbackManager.Factory.create();
        image = BitmapFactory.decodeResource(baseActivity.getResources(),
                R.drawable.profile);

        loginBT.setReadPermissions("public_profile", "email");




        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        try {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                        .addConnectionCallbacks(this)
                        .enableAutoManage(getActivity(), null)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
            }
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }






        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        parent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        underlineText();
        /*apiInterface    = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/

        mainlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                try {
                    CommonDialogs.hideSoftKeyboard(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    private void underlineText() {
        SpannableString content = new SpannableString(getResources().getString(R.string.label_forgot_password));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        forgotPasswordTV.setText(content);
    }



    TwitterAuthClient client;

    @OnClick({R.id.login_BT, R.id.fbLogin_BT, R.id.googleLogin_BT, R.id.forgotPassword_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_BT:
                //baseActivity.commonFunctions.sharePhotoToFacebook(baseActivity, image);
                validateLoginData();
                break;
            case R.id.fbLogin_BT:
                accessUserdata();
               // startActivity(new Intent(baseActivity, FacebookLogin.class));
                break;
            case R.id.googleLogin_BT:

                try {
                    if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, 6);


                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  startActivity(new Intent(baseActivity, GPlusLoginActivity.class));
                break;

            case R.id.forgotPassword_TV:
                baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new ForgotPasswordFragment());
                break;
        }
    }


    public boolean canShare() {
        /*if (this.getShareContent() == null) {
            return false;
        }*/
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Log.e("AcessTokensss", "" + accessToken);
        if (accessToken == null) {
            return false;
        }
        final Set<String> permissions = accessToken.getPermissions();
        if (permissions == null) {
            return false;
        }
        return (permissions.contains("publish_actions"));
    }

    /**
     * MEthod for validating login data ...
     */
    private void validateLoginData() {
        String email = emailPhoneET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        timeZoneCurrent= (timeZone.getID());

        try {
            CommonDialogs.hideSoftKeyboard(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Time zone string","="+timeZoneCurrent);

        if (isValidText(email) && isValidText(password)) {
            if (isValidEmail(email) || isValidMobile(email)) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("password", password);
                map.put("device_id",  mPreferencees.getString("Device_Id",""));
                map.put("device_type", "A");
                map.put("time_zone",timeZoneCurrent);
                map.put("access_token","9a218c9b5dfdae8b5abc11a41905ed48");
                if(isEmailValid(email)){
                    map.put("email", email);
                    map.put("registered_by", "E");
                }else{
                    map.put("phone_number",email);
                    map.put("registered_by", "P");
                }
                Login =  baseActivity.apiInterface.Login(map);
                baseActivity.apiHitAndHandle.makeApiCall(Login, this);

            } else {

                if (!isValidEmail(email)) {
                    showToast("Enter Valid Email or Phone Number");
                } else if (!isValidMobile(email)) {
                    showToast("Enter Valid Email or Phone Number");
                }
            }

        } else {
            if (!isValidText(email)) {
                showToast("Enter Email or Phone Number");
            } else if (!isValidEmail(email)) {
                showToast("Enter valid email");
            } else if (isValidText(password)) {
                showToast("Password cannot be empty");
            }
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private void accessUserdata() {

        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            loginBT.performClick();

        } else {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY).logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }

        loginBT.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (object.has("email")) {

                                emailtext = object.getString("email");

                            } else {
                                emailtext = "";
                            }
                            if (object.has("birthday")) {
                                birthdaytext = object.getString("birthday");
                            }
                            if (object.has("id")) {
                                id = object.getString("id");
                            }

                            if (object.has("first_name")) {
                                firstname = object.getString("first_name");
                                Constants.FirstName = firstname;
                            }
                            if (object.has("last_name")) {
                                lastname = object.getString("last_name");
                                Constants.LastName = lastname;
                            }
                            if (object.has("name")) {
                                name = object.getString("name");

                            }
                            url = "https://graph.facebook.com/" + id + "/picture?type=large";
                            socialLoginType = "F";

                            map.put("social_media_id", id);
                            map.put("first_name", firstname);
                            map.put("last_name", lastname);
                            map.put("register_type", "F");
                            map.put("time_zone",timeZoneCurrent);
                            map.put("device_id", mPreferencees.getString("Device_Id",""));
                            map.put("device_type", "A");

                            map.put("app_version", "0.1");
                            map.put("access_token", "9a218c9b5dfdae8b5abc11a41905ed48");
                            map.put("email", emailtext);
                            if(!url.equals("") || url!= null){
                                new DownloadImage().execute(url);
                            }
                           else{
                                signUp =  baseActivity.apiInterface.Signup(map);
                                baseActivity.apiHitAndHandle.makeApiCall(signUp, LoginFragment.this,true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "id,email,first_name,last_name,birthday,name");
                graphRequest.setParameters(bundle);

                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }

        });


    }

    private void hitApi(Call<JsonObject> call,HashMap<String, String> map) {
        call =  baseActivity.apiInterface.Signup(map);
        baseActivity.apiHitAndHandle.makeApiCall(call, LoginFragment.this,true);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("GOOGLE SIGN IN", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
// Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();


            //     acct.getEmail()
            //    acct.getId()
            //    acct.getPhotoUrl();



            firstname= acct.getGivenName();
            lastname=acct.getFamilyName();
            emailtext=acct.getEmail();
            id =acct.getId();


            Log.e(">>>>>>>>>.image google",">>>>>>>>"+url);
            Constants.FirstName = firstname;
            Constants.LastName = lastname;

            Log.e("Globals.FirstName","=======>"+firstname);
            Log.e("Globals.Lastname","===========>"+lastname);
            Log.e("Globals.Email and id","===========>"+emailtext+">>>"+id);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Log.e("Google all data", "------------  " + acct);
                        //     file = new File(acct.getPhotoUrl().getPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Log.e("Google all data", "------------  " + acct);
                                //     file = new File(acct.getPhotoUrl().getPath());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            socialLoginType = "G";
                            map.put("social_media_id", id);
                            map.put("first_name", firstname);
                            map.put("last_name", lastname);
                            map.put("device_id", mPreferencees.getString("Device_Id",""));
                            map.put("device_type", "A");
                            map.put("app_version", "0.1");
                            map.put("time_zone",timeZoneCurrent);
                            map.put("access_token","9a218c9b5dfdae8b5abc11a41905ed48");
                            map.put("email", emailtext);

                            try {
                                Uri url2 = acct.getPhotoUrl();
                                url = url2.toString();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(!url.equals("") || url!= null){
                                new DownloadImage().execute(url);
                            }
                            else{
                                signUp2 =  baseActivity.apiInterface.Signup(map);
                                baseActivity.apiHitAndHandle.makeApiCall(signUp2, LoginFragment.this,true);
                            }
                            /*signUp2 = apiInterface.Signup(map);
                            apiHitAndHandle.makeApiCall(signUp2,LoginFragment.this,true);*/

                        }
                    });

                }
            });


        } else {
            Log.e("GOOGLE SIGN IN", "handleSignInResult:" + result.isSuccess());
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

            Log.e("ONActivity result come", "------------------actiyity result");

        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body",">>>>>>>>>"+object.toString());

        try {
            JSONObject jsonObject = new JSONObject(object.toString());

            if(call == Login){

                JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                user_id = jsonObject1.getString("_id");
                access_token = jsonObject1.getString("access_token");
                first_name = jsonObject1.getString("first_name");
                last_name = jsonObject1.getString("last_name");
                profile_status = jsonObject1.getString("profile_status");

                BaseModel model = new BaseModel(getActivity());
                model.setAccess_token(access_token);
                model.setLogin_type("O");
                model.setUser_id(user_id);
                model.setUser_fname(first_name);
                model.setUser_lname(last_name);
                model.setProfile_status(profile_status);
                model.setLogin(true);
                GeneralValues.set_Access_Key(getActivity(), access_token);
                GeneralValues.set_user_id(getActivity(), user_id);

                GeneralValues.set_logintype(getActivity(), "O");
                if (profile_status.equals("false")) {
                    baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new CreateProfile_PersonalFragment());
                } else {
                    GeneralValues.set_loginbool(getActivity(), true);
                    startActivity(new Intent(baseActivity, HomeActivity.class).putExtra("From", "login"));
                    getActivity().finish();
                }
            }else if(call == signUp){
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    user_id = jsonObject1.getString("_id");
                    access_token = jsonObject1.getString("access_token");
                    first_name = jsonObject1.getString("first_name");
                    last_name = jsonObject1.getString("last_name");
                    profile_status = jsonObject1.getString("profile_status");

                    GeneralValues.set_Access_Key(getActivity(),access_token);
                    GeneralValues.set_user_id(getActivity(),user_id);
                    GeneralValues.set_logintype(getActivity(),"F");
                    if(profile_status.equals("false")){
                        Bundle args = new Bundle();
                        args.putString("img",Constants.FBImage);

                        baseActivity.navigateFragmentTransaction_ARG(R.id.authViewContainer, new CreateProfile_PersonalFragment(),args);
                    }
                    else{
                        GeneralValues.set_loginbool(getActivity(),true);
                        startActivity(new Intent(baseActivity, HomeActivity.class).putExtra("From", "login"));
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            else if(call == signUp2){
                try {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    user_id = jsonObject1.getString("_id");
                    access_token = jsonObject1.getString("access_token");
                    first_name = jsonObject1.getString("first_name");
                    last_name = jsonObject1.getString("last_name");
                    profile_status = jsonObject1.getString("profile_status");
                    GeneralValues.set_Access_Key(getActivity(),access_token);
                    GeneralValues.set_user_id(getActivity(),user_id);

                    GeneralValues.set_logintype(getActivity(),"G");
                    if(profile_status.equals("false")){
                        baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new CreateProfile_PersonalFragment());
                    }
                    else{
                        GeneralValues.set_loginbool(getActivity(),true);
                        startActivity(new Intent(baseActivity, HomeActivity.class).putExtra("From", "login"));
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {



        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Create a progressdialog
        }

        @Override
        protected Bitmap doInBackground(String... URL)
        {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try
            {
                // / Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            imageurl = CommonDialogs.saveimagetosdcard(getActivity(), result);
            if(imageurl !=null || !imageurl.equals("")) {
                MemoryCacheUtils.removeFromCache("" + imageurl, ImageLoader.getInstance().getMemoryCache());
                DiskCacheUtils.removeFromCache("" + imageurl, ImageLoader.getInstance().getDiskCache());
                Constants.FBImage = imageurl;
            }else{
                Constants.FBImage ="";
            }
          if(socialLoginType.equals("F")){
              signUp =  baseActivity.apiInterface.Signup(map);
              baseActivity.apiHitAndHandle.makeApiCall(signUp, LoginFragment.this,true);
          }else{
              signUp2 =  baseActivity.apiInterface.Signup(map);
              baseActivity.apiHitAndHandle.makeApiCall(signUp2, LoginFragment.this,true);
          }

            Log.e(">>>>>>>>>>Fb image",">>>>>>>>>>"+Constants.FBImage);
        }
    }
}
