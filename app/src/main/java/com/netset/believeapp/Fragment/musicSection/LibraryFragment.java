package com.netset.believeapp.Fragment.musicSection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by netset on 22/1/18.
 */

public class LibraryFragment extends BaseFragment {
    @BindView(R.id.label_recent)
    TextView labelRecent;
    @BindView(R.id.albumIcon_IV)
    ImageView albumIconIV;
    @BindView(R.id.albumName_TV)
    TextView albumNameTV;

    @BindView(R.id.player_label)
    RelativeLayout playerLabel;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.music_library_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        albumIconIV.setImageResource(R.drawable.ic_recent_add);
        albumNameTV.setText(getResources().getString(R.string.label_recently_added));
    }



    @OnClick({R.id.player_label})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.player_label:
                Bundle args = new Bundle();
                args.putString("From","recent");
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new RecentAddedListFragment(),args);
                break;

        }
    }

   //
}
