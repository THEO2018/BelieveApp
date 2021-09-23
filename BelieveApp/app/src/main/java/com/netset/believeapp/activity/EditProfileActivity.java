package com.netset.believeapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.netset.believeapp.Fragment.userProfile.EditProfile_PersonalFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import static com.netset.believeapp.R.drawable.ic_back;

/**
 * Created by netset on 2/2/18.
 */

public class EditProfileActivity extends BaseActivity {

    String screen_tag = "";
    public Toolbar toolbar;
    public TextView menu_IM;
    public TextView toolbar_title, actionText_TV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);

        setUpToolbar();
        navigateFragmentTransaction(R.id.editViewContainer, new EditProfile_PersonalFragment());
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
            actionText_TV.setText("Next");
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
                    CommonDialogs.hideSoftKeyboard(EditProfileActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.editViewContainer);
        if (fragment instanceof EditProfile_PersonalFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}
