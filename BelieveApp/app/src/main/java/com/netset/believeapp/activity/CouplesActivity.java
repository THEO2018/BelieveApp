package com.netset.believeapp.activity;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.netset.believeapp.Fragment.MarriageSection.AddFianceFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;

import static com.netset.believeapp.R.drawable.ic_back;

/**
 * Created by netset on 18/5/18.
 */

public class CouplesActivity extends BaseActivity {

    String screen_tag = "";
    public Toolbar toolbar;
    public TextView menu_IM;
    public TextView toolbar_title, actionText_TV;
    public static boolean isRefresh = false;
    SharedPreferences.Editor mEdit;
    SharedPreferences mPreferencees;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        mPreferencees = getApplicationContext().getSharedPreferences("Believe", MODE_PRIVATE);
        mEdit         = mPreferencees.edit();
        setUpToolbar();
        navigateFragmentTransaction(R.id.editViewContainer, new AddFianceFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEdit.putString("couplebool","true");
        mEdit.commit();
        NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nMgr.cancel(2);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mEdit.putString("couplebool","false");
        mEdit.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CouplesActivity.isRefresh = true;
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
    public void setToolbarTitle(String title, boolean isBack, boolean isRightMenu) {

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
          /*  actionText_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCreateProfileResponse();
                }
            });*/
        } else {
            actionText_TV.setVisibility(View.INVISIBLE);
        }

        menu_IM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    CommonDialogs.hideSoftKeyboard(CouplesActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                // onBackPressed();
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
        finish();

    }


}
