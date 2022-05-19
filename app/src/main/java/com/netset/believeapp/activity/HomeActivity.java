package com.netset.believeapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.netset.believeapp.CommonConst;
import com.netset.believeapp.Fragment.HomeFragment;
import com.netset.believeapp.Fragment.classifiedSection.JobDescriptionFragment;
import com.netset.believeapp.Fragment.classifiedSection.JobsFragment;
import com.netset.believeapp.R;

import static com.netset.believeapp.R.drawable.ic_back;

/**
 * Created by netset on 9/1/18.
 */

public class HomeActivity extends BaseActivity {

    public Toolbar toolbar;
    public static TextView menu_IM;
    public static TextView toolbar_title, actionText_TV;
    public static Spinner selecter_SPN;
    public static ImageView title_IM;

    int backPressValue = 0;
    public static boolean isPostDetailVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpToolbar();
        if (savedInstanceState==null){

            Intent in = getIntent();
            if(in.getStringExtra("From").equals("liked") ) {
                Bundle b = new Bundle();
                b.putString("From", in.getStringExtra("From"));
                b.putString("id", in.getStringExtra("id"));
                navigateFragmentTransaction_ARG(R.id.homeContainer, new HomeFragment(),b);
            }
            else{
                navigateFragmentTransaction(R.id.homeContainer, new HomeFragment());
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        divView = findViewById(R.id.divView);
        setSupportActionBar(toolbar);
        View logo = getLayoutInflater().inflate(R.layout.custom_view_toolbar_home, null);
        toolbar.addView(logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
    }

    public void setToolbarSpinner(){
        selecter_SPN.setVisibility(View.VISIBLE);
    }

    public static void setSpinnerClick(AdapterView.OnItemSelectedListener listener){
         selecter_SPN.setOnItemSelectedListener(listener);
    }

    public static void setSpinnerItemClick(AdapterView.OnItemClickListener listener){
        selecter_SPN.setOnItemClickListener(listener);
    }


    @SuppressLint("NewApi")
    public void setToolbarTitle(String title, boolean isBack, boolean isRightMenu, boolean isFromHome, final OnCreateProfileListener callback) {

        divView = findViewById(R.id.divView);
        menu_IM = toolbar.findViewById(R.id.menu_icon);
        toolbar_title = toolbar.findViewById(R.id.tv_toolbar_title);
        actionText_TV = toolbar.findViewById(R.id.actionNext_TV);
        title_IM = toolbar.findViewById(R.id.title_IM);
        selecter_SPN = toolbar.findViewById(R.id.selecter_SPN);

        selecter_SPN.setVisibility(View.GONE);
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
            divView.setVisibility(View.VISIBLE);
        }
        toolbar_title.setText(title);

        if (isBack) {
            actionText_TV.setVisibility(View.INVISIBLE);
            menu_IM.setText("");
            menu_IM.setBackground(getResources().getDrawable(ic_back));
        } else {
            if (!isFromHome) {
                actionText_TV.setVisibility(View.VISIBLE);
                menu_IM.setBackgroundResource(R.drawable.ic_menu);
                title_IM.setImageResource(0);
            } else {
                actionText_TV.setVisibility(View.INVISIBLE);
                menu_IM.setText("");
                menu_IM.setBackgroundResource(0);
                toolbar_title.setText("");
                title_IM.setImageResource(R.drawable.ic_believe_home);

            }
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
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.homeContainer);
        Handler handler=new Handler();

        if (fragment instanceof HomeFragment) {
            backPressValue++;
            if (backPressValue == 2) {
                finish();
                backPressValue = 0;
            } else {
                showToast("Press again to exit.");
            }
            handler.postDelayed(() -> backPressValue=0,1500);

        }
        else {
            super.onBackPressed();
        }
    }



}
