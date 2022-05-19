package com.netset.believeapp.sociallogin;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by neeraj.narwal on 9/8/16.
 */
public class GPlusLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private static final int RC_SIGN_IN = 123;
    private static final int G_PLUS_SIGNIN_REQ_CODE = 1212;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionResult mConnectionResult;
    private SocialLogin socialLogin;
    GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socialLogin = SocialLogin.getInstance();
        createGoogleClient();
    }

    private void createGoogleClient() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        try {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(GPlusLoginActivity.this)
                        .addConnectionCallbacks(this)
                        .enableAutoManage(GPlusLoginActivity.this, null)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
            }
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void resetAndLogin() {


        try {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, G_PLUS_SIGNIN_REQ_CODE);


        } catch (Exception e) {
            e.printStackTrace();
        }

        /*Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        signInWithGplus();
                    }
                });*/
    }

  /*  private void signInWithGplus() {
        if (!mGoogleApiClient.isConnected()) {
            resolveSignInError();
        } else {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, G_PLUS_SIGNIN_REQ_CODE);
        }
    }*/

    private void resolveSignInError() {
        if (mConnectionResult != null) {
            if (mConnectionResult.hasResolution()) {
                try {
                    mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
                } catch (IntentSender.SendIntentException e) {
                    mGoogleApiClient.connect();
                }
            }
        } else {
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == G_PLUS_SIGNIN_REQ_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            try {
                if (result.isSuccess()) {
                    GoogleSignInAccount acct = result.getSignInAccount();
                    if (acct != null)
                        socialLogin.gPlusLoginDone(acct);
                    finish();
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.mConnectionResult = connectionResult;
        resolveSignInError();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        resetAndLogin();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
