package com.netset.believeapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.netset.believeapp.Fragment.userAuthentication.LoginFragment;
import com.netset.believeapp.Fragment.userAuthentication.SignupFragment;
import com.netset.believeapp.Fragment.userAuthentication.TypeChooserFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import butterknife.ButterKnife;

import static com.netset.believeapp.R.drawable.ic_back;
import static com.netset.believeapp.Utils.Constants.LOGIN_TAG;
import static com.netset.believeapp.Utils.Constants.SIGNUP_TAG;

/**
 * Created by netset on 8/1/18.
 */

public class UserAuthenticationActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView menu_IM;
    public TextView toolbar_title, actionText_TV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);
        ButterKnife.bind(this);

        setUpToolbar();
        runtimePermissions();
        navigateFragmentTransaction(R.id.authViewContainer, new TypeChooserFragment());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        divView = findViewById(R.id.divView);
        setSupportActionBar(toolbar);
        View logo = getLayoutInflater().inflate(R.layout.custom_view_toolbar, null);
        toolbar.addView(logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
    }

    private void selectFragment(String screen_tag) {
        if (screen_tag.equalsIgnoreCase(LOGIN_TAG)) {
            navigateFragmentTransaction(R.id.authViewContainer, new LoginFragment());
        } else if (screen_tag.equalsIgnoreCase(SIGNUP_TAG)) {
            navigateFragmentTransaction(R.id.authViewContainer, new SignupFragment());
        }
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.authViewContainer);
        if (fragment instanceof TypeChooserFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NewApi")
    public void setToolbarTitle(String title, boolean isBack, boolean isRightMenu, final OnCreateProfileListener callback) {

        divView = findViewById(R.id.divView);
        menu_IM = toolbar.findViewById(R.id.menu_icon);
        toolbar_title = toolbar.findViewById(R.id.tv_toolbar_title);
        actionText_TV = toolbar.findViewById(R.id.actionNext_TV);
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
            divView.setVisibility(View.VISIBLE);
        }
        toolbar_title.setText(title);
        if (isBack) {
            actionText_TV.setVisibility(View.INVISIBLE);
            divView.setVisibility(View.VISIBLE);
            menu_IM.setText("");
            menu_IM.setBackground(getResources().getDrawable(ic_back));
        } else {
            actionText_TV.setVisibility(View.VISIBLE);
            divView.setVisibility(View.GONE);
            menu_IM.setText("Cancel");
            menu_IM.setTextColor(getResources().getColor(R.color.white));
            menu_IM.setBackgroundResource(0);
        }

        if (isRightMenu) {
            actionText_TV.setVisibility(View.VISIBLE);
            actionText_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCreateProfileResponse();
                }
            });
        } else {
            actionText_TV.setVisibility(View.INVISIBLE);
        }

        menu_IM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    CommonDialogs.hideSoftKeyboard(UserAuthenticationActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                onBackPressed();
            }
        });

    }

    public void hideToolbar() {
        toolbar.setVisibility(View.INVISIBLE);
        divView.setVisibility(View.INVISIBLE);
    }


}
