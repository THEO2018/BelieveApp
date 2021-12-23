package com.netset.believeapp.Fragment.userAuthentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.UserAuthenticationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by netset on 8/1/18.
 */

public class TypeChooserFragment extends BaseFragment {

    @BindView(R.id.login_BT)
    AppCompatButton loginBT;
    @BindView(R.id.signup_BT)
    AppCompatButton signupBT;
    @BindView(R.id.appInfo_IM)
    AppCompatImageView appInfoIM;
    Unbinder unbinder;
    @BindView(R.id.parent)
    LinearLayout parent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_type_chooser, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((UserAuthenticationActivity)getActivity()).hideToolbar();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parent.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @OnClick({R.id.login_BT, R.id.signup_BT, R.id.appInfo_IM})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_BT:
                baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new LoginFragment());
                break;
            case R.id.signup_BT:
                baseActivity.navigateFragmentTransaction(R.id.authViewContainer, new SignupFragment());
                break;
            case R.id.appInfo_IM:
                Intent browserIntent = null;
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                getActivity().startActivity(browserIntent);


                break;
        }
    }


}
