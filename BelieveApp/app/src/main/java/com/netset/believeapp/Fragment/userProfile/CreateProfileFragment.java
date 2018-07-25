package com.netset.believeapp.Fragment.userProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.BaseActivity;
import com.netset.believeapp.activity.UserAuthenticationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.netset.believeapp.Utils.Constants.SC_CREATE_PROFILE;

/**
 * Created by netset on 8/1/18.
 */

public class CreateProfileFragment extends BaseFragment implements BaseActivity.OnCreateProfileListener {

   /* @BindView(R.id.profileViewContainer)
    FrameLayout profileViewContainer;*/
    @BindView(R.id.createProfile_Parent)
    ConstraintLayout createProfileParent;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_profile_fragment, null);

        ((UserAuthenticationActivity)getActivity()).setToolbarTitle(SC_CREATE_PROFILE,
                false, true,this);
        baseActivity.RegisterCreateProfileListener(this);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // baseActivity.navigateFragmentTransaction(R.id.profileViewContainer, new CreateProfile_PersonalFragment());
    }


    @Override
    public void onCreateProfileResponse() {
      showLog("click","asfasf");


    }
}
