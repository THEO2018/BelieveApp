package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.CommentAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.GsonModel.PrayerDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 4/5/18.
 */

public class ShowPollCommentsFragment extends BaseFragment implements ApiResponse {

@BindView(R.id.weddingList_RV)
    RecyclerView commentList_RV;
@BindView(R.id.main_lay)
    RelativeLayout mainLay;
    @BindView(R.id.lable_TV)
    TextView lableTV;
    Unbinder unbinder;


    Call<JsonObject>  GetComments,GetPrayerDetail;
    PollsDetailModel result;
    CommentAdapter commentAdapter;
    PrayerDetailModel result2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wedding_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Comments", true, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        lableTV.setVisibility(View.GONE);
        GetPollDetail();


    }


    public void GetPollDetail() {
        Bundle b = getArguments();
        if(b.getString("title").equals("poll")){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("poll_id", b.getString("pollId"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetComments = baseActivity.apiInterface.Get_PollDetail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetComments, this);
        }
        else{
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("prayer_id", b.getString("pollId"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetPrayerDetail = baseActivity.apiInterface.Get_Prayers_Detail(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetPrayerDetail, this);
        }

    }

    @Override
    public void onSuccess(Call call, Object object) {


        if(call == GetComments) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), PollsDetailModel.class);


            commentAdapter = new CommentAdapter(baseActivity, result.data.comments, true, "poll");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            commentList_RV.setLayoutManager(mLayoutManager);
            commentList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
            commentList_RV.setItemAnimator(new DefaultItemAnimator());
            commentList_RV.setAdapter(commentAdapter);

            commentList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, commentList_RV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            Bundle args = new Bundle();
                            args.putString("userId", result.data.comments.get(position).userId.id);
                            args.putString("from", "poll");
                            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(), args);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

            if (result.data.comments.size() == 0) {
                mainLay.setBackgroundResource(R.color.transparent_white);
            } else {
                mainLay.setBackgroundResource(R.drawable.shape_pink_outline_solid);
            }

        }
        else if(call ==GetPrayerDetail){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result2 = gson.fromJson(object.toString(), PrayerDetailModel.class);


            commentAdapter = new CommentAdapter(baseActivity, result2.data.comments, true, "pray");
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
            commentList_RV.setLayoutManager(mLayoutManager);
            commentList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
            commentList_RV.setItemAnimator(new DefaultItemAnimator());
            commentList_RV.setAdapter(commentAdapter);

            commentList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, commentList_RV,
                    new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {

                            Bundle args = new Bundle();
                            args.putString("userId", result2.data.comments.get(position).userId.id);
                            args.putString("from", "pray");
                            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(), args);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

            if (result2.data.comments.size() == 0) {
                mainLay.setBackgroundResource(R.color.transparent_white);
            } else {
                mainLay.setBackgroundResource(R.drawable.shape_pink_outline_solid);
            }
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
