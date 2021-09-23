package com.netset.believeapp.Fragment.birthdaySection;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by netset on 3/5/18.
 */

public class NewBornDetailFragment extends BaseFragment {



    @BindView(R.id.bornImage_IV)
    ImageView bornImageIV;
    @BindView(R.id.catText_View)
    LinearLayout catTextView;
    @BindView(R.id.bornName_TV)
    TextView bornNameTV;
    @BindView(R.id.bornAuthor_TV)
    TextView bornAuthorTV;
    @BindView(R.id.bornText_TV)
    TextView bornTextTV;
    Unbinder unbinder;
    String image,title,desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.newborndetail_fragment, null);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         Bundle b = getArguments();
         if(b != null){

             image = b.getString("image");
             title = b.getString("title");
             desc = b.getString("detail");

             ((HomeActivity) getActivity()).setToolbarTitle(title, true, false, false, null);
             CommonDialogs.getSquareImage(getActivity(),image,bornImageIV);
             bornNameTV.setText(title);
             bornTextTV.setText(desc);
         }


    }
}
