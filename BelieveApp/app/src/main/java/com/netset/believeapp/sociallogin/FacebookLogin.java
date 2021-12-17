package com.netset.believeapp.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.netset.believeapp.BuildConfig;
import com.netset.believeapp.R;

import org.json.JSONObject;

import java.util.Arrays;


public class FacebookLogin extends AppCompatActivity{
    private CallbackManager callbackmanager;
    private GraphRequest request;
    private LoginButton fbLoginButton;
    private SocialLogin socialLogin;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    String first_name,last_name,access_token,user_id,profile_status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackmanager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.social_facebook_login);

       /* apiInterface    = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(FacebookLogin.this);*/
        socialLogin = SocialLogin.getInstance();
        fbLoginButton = (LoginButton) findViewById(R.id.fbLoginButton);

        fbLoginButton.setReadPermissions("public_profile", "email");
        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFacebook();
            }
        });
        fbLoginButton.callOnClick();
    }

    public void initFacebook() {

        try {
            LoginManager.getInstance().logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            fbLoginButton.performClick();
        }
     /*   else {
            LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_VIEW_ONLY).logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }*/

        fbLoginButton.registerCallback(callbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        socialLogin.facebookLoginDone(object, response);
                          finish();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location, picture.width(120).height(120)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                log("Facebook login cancel");
                finish();
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                log("Facebook error :" + error.toString());
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackmanager.onActivityResult(requestCode, resultCode, data);

    }

    private void log(String s) {
        if (BuildConfig.DEBUG) {
            Log.e("Facebook Login : >> ", s);
        }
    }


}