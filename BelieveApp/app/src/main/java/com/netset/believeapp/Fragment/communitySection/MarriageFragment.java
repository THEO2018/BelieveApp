package com.netset.believeapp.Fragment.communitySection;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.CoupleListAdapter;
import com.netset.believeapp.Adapter.communityAdapters.WeddingListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.MarriageSection.AdviceFragment;
import com.netset.believeapp.Fragment.MarriageSection.UpcomingWeddingFragment;
import com.netset.believeapp.Fragment.MarriageSection.WeddingFragment;
import com.netset.believeapp.Fragment.blogSection.BlogDetailFragment;
import com.netset.believeapp.Fragment.homeMenu.EventDetailFragment;
import com.netset.believeapp.GsonModel.MarriagesModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.CouplesActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 18/1/18.
 */

public class MarriageFragment extends BaseFragment implements ApiResponse {


    @BindView(R.id.viewAll_TV)
    TextView viewAllTV;
    @BindView(R.id.coupleList_RV)
    RecyclerView coupleListRV;
    @BindView(R.id.viewAllWedList_TV)
    TextView viewAllWedListTV;
    @BindView(R.id.wedList_RV)
    RecyclerView wedListRV;
    @BindView(R.id.viewAllUpcoming_TV)
    TextView viewAllUpcomingTV;
    @BindView(R.id.upcoming_IV)
    ImageView upcomingImg_IV;
    @BindView(R.id.advLabel_TV)
    TextView adviceLabelTV;
    @BindView(R.id.viewAllAdvice_TV)
    TextView viewAllAdviceTV;
    @BindView(R.id.advice_IV)
    ImageView adviceIV;
    @BindView(R.id.advLayout)
    RelativeLayout adviceLayout;
    Unbinder unbinder;
    @BindView(R.id.title_label_TV)
    TextView titleLabelTV;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.wedding_label_TV)
    TextView weddingLabelTV;
    @BindView(R.id.weddingListLayout)
    RelativeLayout weddingListLayout;
    @BindView(R.id.upcoming_label_TV)
    TextView upcomingLabelTV;
    @BindView(R.id.upcomingWeddingLayout)
    RelativeLayout upcomingWeddingLayout;
    @BindView(R.id.marriageView_Container)
    ScrollView marriageViewContainer;
    @BindView(R.id.noupcoming_TV)
    TextView noupcomingTV;
    @BindView(R.id.noadvice_TV)
    TextView noadviceTV;

    CoupleListAdapter coupleListAdapter;
    WeddingListAdapter weddingListAdapter;
    MarriagesModel result;
    Call<JsonObject> GetMarriageList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.marriage_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        marriageViewContainer.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewAllSpannable();

    }

    @Override
    public void onStart() {
        super.onStart();
        CallApi();
    }

    public void CallApi() {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetMarriageList = baseActivity.apiInterface.GetMarriages(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetMarriageList, this);
    }


    private void viewAllSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllTV.setTextColor(getResources().getColor(R.color.black));
        viewAllTV.setText(content);
        viewAllWEdListSpannable();
    }

    private void viewAllWEdListSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_WedList_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllWedListTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllWedListTV.setText(content);
        viewAllUpcomingSpannable();
    }

    private void viewAllUpcomingSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_Upcoming_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllUpcomingTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllTV.setTextColor(getResources().getColor(R.color.black));
        viewAllUpcomingTV.setText(content);
        viewAllAdviceSpannable();

    }

    private void viewAllAdviceSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_Advice_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllAdviceTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllAdviceTV.setText(content);
    }

    ClickableSpan clickableSpan_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {

            startActivity(new Intent(getActivity(), CouplesActivity.class));
            //   baseActivity.navigateFragmentTransaction(R.id.homeContainer, new AddFianceFragment());
        }
    };

    ClickableSpan clickableSpan_WedList_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new WeddingFragment());
        }
    };

    ClickableSpan clickableSpan_Advice_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new AdviceFragment());
        }
    };


    ClickableSpan clickableSpan_Upcoming_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new UpcomingWeddingFragment());
        }
    };


    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), MarriagesModel.class);


        //----------------Betrotheds list adapter---------------
        coupleListAdapter = new CoupleListAdapter(baseActivity, result.getData().getBetrotheds(), false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
        coupleListRV.setLayoutManager(mLayoutManager);
        coupleListRV.setItemAnimator(new DefaultItemAnimator());
        coupleListRV.setAdapter(coupleListAdapter);

//----------------wedding list adapter---------------

        weddingListAdapter = new WeddingListAdapter(baseActivity, result.getData().getWeddingLists(), false);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
        wedListRV.setLayoutManager(mLayoutManager1);
        wedListRV.setItemAnimator(new DefaultItemAnimator());
        wedListRV.setAdapter(weddingListAdapter);

        if (result.getData().getWeddingLists().size() == 0) {

            viewAllWedListTV.setVisibility(View.GONE);
        } else {
            viewAllWedListTV.setVisibility(View.VISIBLE);
        }

        if (result.getData().getUpcomingMarriages().size() == 0) {
            noupcomingTV.setVisibility(View.VISIBLE);
            viewAllUpcomingTV.setVisibility(View.GONE);
            upcomingImg_IV.setVisibility(View.GONE);
        } else {
            noupcomingTV.setVisibility(View.GONE);
            viewAllUpcomingTV.setVisibility(View.VISIBLE);
            upcomingImg_IV.setVisibility(View.VISIBLE);
        }


        if (result.getData().getAdvice().size() == 0) {
            noadviceTV.setVisibility(View.VISIBLE);
            viewAllAdviceTV.setVisibility(View.GONE);
            adviceIV.setVisibility(View.GONE);
        } else {
            noadviceTV.setVisibility(View.GONE);
            viewAllAdviceTV.setVisibility(View.VISIBLE);
            adviceIV.setVisibility(View.VISIBLE);
        }


        wedListRV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, wedListRV,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Uri uri = Uri.parse(result.getData().getWeddingLists().get(position).getUrlOnlineSite()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


        /////----------upcoming wedding---------
        CommonDialogs.getSquareImage2(getActivity(), result.getData().getUpcomingMarriages().get(0).eventCover, upcomingImg_IV);



        upcomingImg_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventDetailFragment eventDetailFragment = new EventDetailFragment();
                Bundle args = new Bundle();
                args.putString("eventid", result.getData().getUpcomingMarriages().get(0).id);
                eventDetailFragment.setArguments(args);
                baseActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeContainer, eventDetailFragment, "")
                        .addToBackStack("event")
                        .commit();
            }
        });



        ///// -------Advice--------
        CommonDialogs.getSquareImage2(getActivity(), result.getData().getAdvice().get(0).blogImage, adviceIV);

        adviceIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("blogid",result.getData().getAdvice().get(0).id);
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new BlogDetailFragment(),args);
            }
        });


    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
