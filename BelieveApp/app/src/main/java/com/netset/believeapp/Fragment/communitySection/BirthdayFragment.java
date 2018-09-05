package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.communityAdapters.BdayProListAdapter;
import com.netset.believeapp.Adapter.communityAdapters.BirthdayAdapter;
import com.netset.believeapp.Adapter.communityAdapters.NewBornAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.AllBirthdayFragment;
import com.netset.believeapp.Fragment.birthdaySection.BirthdayProductFragment;
import com.netset.believeapp.Fragment.birthdaySection.NewBornDetailFragment;
import com.netset.believeapp.Fragment.birthdaySection.NewBornFragment;
import com.netset.believeapp.Fragment.homeMenu.WebViewFragment;
import com.netset.believeapp.GsonModel.BirthdayModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 18/1/18.
 */

public class BirthdayFragment extends BaseFragment implements ApiResponse {


    Unbinder unbinder;
    @BindView(R.id.bdayLogo)
    ImageView bdayLogo;
    @BindView(R.id.optioNCont)
    RelativeLayout optioNCont;
    @BindView(R.id.bdayList_RV)
    RecyclerView bdayList_RV;
    @BindView(R.id.moreBday_TV)
    TextView moreBday_TV;
    @BindView(R.id.bdayList_Container)
    LinearLayout bdayListContainer;
    @BindView(R.id.label_TV)
    TextView labelTV;
    @BindView(R.id.viewAllWBday_TV)
    TextView viewAllWBdayTV;
    @BindView(R.id.bdayProList_RV)
    RecyclerView bdayProList_RV;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.newBorn_label_TV)
    TextView newBornLabelTV;
    @BindView(R.id.newBornList_TV)
    TextView newBornList_TV;
    @BindView(R.id.newBornList_RV)
    RecyclerView newBornList_RV;
    @BindView(R.id.newBornVIew)
    RelativeLayout newBornVIew;
    @BindView(R.id.birthdayView_Container)
    ScrollView birthdayViewContainer;
    @BindView(R.id.today_TV)
    TextView todayTV;
    @BindView(R.id.todaysView)
    View todaysView;
    @BindView(R.id.upcoming_TV)
    TextView upcomingTV;
    @BindView(R.id.upcomingView)
    View upcomingView;
    @BindView(R.id.nodata_TV)
    TextView nodataTV;


    Call<JsonObject> GetBirthdayList;
    List<BlogsModel> blogList = new ArrayList<>();
    BirthdayModel result;
    BirthdayAdapter birthdayAdapter;
    BdayProListAdapter bdayProListAdapter;
    NewBornAdapter newBornAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.birthday_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        birthdayViewContainer.setBackgroundResource(0);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewMoreBdaySpannable();
        viewBdayPrdctSpannable();
        viewAllNewsSpannable();
    }


    @Override
    public void onStart() {
        super.onStart();
        CallApi();
    }

    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetBirthdayList = baseActivity.apiInterface.Get_Birthdays(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetBirthdayList, this);


    }
    private void viewMoreBdaySpannable() {
        String viewAll_TEXT = "2 more persons birthday today";
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.light_pink)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        moreBday_TV.setMovementMethod(LinkMovementMethod.getInstance());
        moreBday_TV.setTextColor(getResources().getColor(R.color.light_pink));
        moreBday_TV.setText(content);
    }

    private void viewBdayPrdctSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_viewProduct, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllWBdayTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllWBdayTV.setTextColor(getResources().getColor(R.color.white));
        viewAllWBdayTV.setText(content);
    }

    private void viewAllNewsSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_moreNews, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        newBornList_TV.setMovementMethod(LinkMovementMethod.getInstance());
        newBornList_TV.setTextColor(getResources().getColor(R.color.white));
        newBornList_TV.setText(content);
    }



    ClickableSpan clickableSpan_moreNews = new ClickableSpan() {
        @Override
        public void onClick(View widget) {

            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new NewBornFragment());
        }
    };

    ClickableSpan clickableSpan_viewProduct = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            baseActivity.navigateFragmentTransaction(R.id.homeContainer, new BirthdayProductFragment());
        }
    };


    private void loadtodayList(){
        birthdayAdapter = new BirthdayAdapter(baseActivity, result.sendData.todayBirthdays, false,"today");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        bdayList_RV.setLayoutManager(mLayoutManager);
        bdayList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        bdayList_RV.setItemAnimator(new DefaultItemAnimator());
        bdayList_RV.setAdapter(birthdayAdapter);
        bdayList_RV.setNestedScrollingEnabled(false);

        if(result.sendData.todayBirthdays.size() >2){
            String brday = ""+((result.sendData.todayBirthdays.size())-2);
            moreBday_TV.setText(brday+" more person's birthday today");
            moreBday_TV.setVisibility(View.VISIBLE);
            nodataTV.setVisibility(View.GONE);

        }
        else if(result.sendData.todayBirthdays.size() == 0){
            nodataTV.setVisibility(View.VISIBLE);
            nodataTV.setText("Nobody's Birthday Today");
            moreBday_TV.setVisibility(View.GONE);
        }
        else{
            moreBday_TV.setVisibility(View.VISIBLE);
            moreBday_TV.setText("View more person's birthday");
            nodataTV.setVisibility(View.GONE);
        }


      moreBday_TV.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Bundle arg = new Bundle();
              arg.putString("type","today");
              baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new AllBirthdayFragment(),arg);
          }
      });

    }


    private void loadupcomingList() {
        birthdayAdapter = new BirthdayAdapter(baseActivity, result.sendData.upcomingBirthdays, false,"upcoming","");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        bdayList_RV.setLayoutManager(mLayoutManager);
        bdayList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        bdayList_RV.setItemAnimator(new DefaultItemAnimator());
        bdayList_RV.setAdapter(birthdayAdapter);
        bdayList_RV.setNestedScrollingEnabled(false);


        if(result.sendData.upcomingBirthdays.size() >2){
            String brday = ""+((result.sendData.upcomingBirthdays.size())-2);
            moreBday_TV.setText(brday+" more upcoming person's birthday");
            nodataTV.setVisibility(View.GONE);
            moreBday_TV.setVisibility(View.VISIBLE);
        }
        else if(result.sendData.upcomingBirthdays.size() == 0){
            nodataTV.setVisibility(View.VISIBLE);
            moreBday_TV.setVisibility(View.GONE);
            nodataTV.setText("Nobody's Birthday Found");
        }

        else{
            moreBday_TV.setVisibility(View.VISIBLE);
            moreBday_TV.setText("View more person's birthday");
            nodataTV.setVisibility(View.GONE);
        }

        moreBday_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("type","upcoming");
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new AllBirthdayFragment(),arg);
            }
        });


    }

    private void loadWeddingProductList() {
        bdayProListAdapter = new BdayProListAdapter(baseActivity, result.sendData.birthdaylists, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity, LinearLayoutManager.HORIZONTAL, false);
        bdayProList_RV.setLayoutManager(mLayoutManager);
        bdayProList_RV.setItemAnimator(new DefaultItemAnimator());
        bdayProList_RV.setAdapter(bdayProListAdapter);
        bdayProList_RV.setNestedScrollingEnabled(false);

        if(result.sendData.birthdaylists.size()==0){
            viewAllWBdayTV.setVisibility(View.GONE);
            labelTV.setVisibility(View.GONE);
        }else{
            viewAllWBdayTV.setVisibility(View.VISIBLE);
            labelTV.setVisibility(View.VISIBLE);
        }
        bdayProList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, bdayProList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle args = new Bundle();
                args.putString("newslink",result.sendData.birthdaylists.get(position).bdayListUrlOnlineSite);
                args.putString("newsname",result.sendData.birthdaylists.get(position).bdayListTitle);
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void loadNewBornList() {
        newBornAdapter = new NewBornAdapter(baseActivity, result.sendData.birthNews, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        newBornList_RV.setLayoutManager(mLayoutManager);
        newBornList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        newBornList_RV.setItemAnimator(new DefaultItemAnimator());
        newBornList_RV.setAdapter(newBornAdapter);
        newBornList_RV.setNestedScrollingEnabled(false);

        if(result.sendData.birthNews.size()==0){
            newBornList_TV.setVisibility(View.GONE);
            newBornLabelTV.setVisibility(View.GONE);
        }else{
            newBornList_TV.setVisibility(View.VISIBLE);
            newBornLabelTV.setVisibility(View.VISIBLE);
        }

        newBornList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, newBornList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle args = new Bundle();
                args.putString("image",result.sendData.birthNews.get(position).classifiedImage);
                args.putString("title",result.sendData.birthNews.get(position).classifiedTitle);
                args.putString("detail",result.sendData.birthNews.get(position).classified);
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new NewBornDetailFragment(),args);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @OnClick({R.id.today_TV, R.id.upcoming_TV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.today_TV:
                displayView(0);
                break;
            case R.id.upcoming_TV:
                displayView(1);
                break;
        }
    }

    public void displayView(int value) {
        switch (value) {
            case 0:
                showTodaysData();
                break;
            case 1:
                showUpcomingData();
                break;
        }
    }

    private void showTodaysData() {

        String sourceString = "<b>Today's</b> ";
        todayTV.setText(Html.fromHtml(sourceString));
        upcomingTV.setText("Upcoming");
        todaysView.setVisibility(View.VISIBLE);
        upcomingView.setVisibility(View.INVISIBLE);
        loadtodayList();

    }

    private void showUpcomingData() {
        String sourceString = "<b>Upcoming</b> ";
        upcomingTV.setText(Html.fromHtml(sourceString));
        todayTV.setText("Today's");
        upcomingView.setVisibility(View.VISIBLE);
        todaysView.setVisibility(View.INVISIBLE);
        loadupcomingList();
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), BirthdayModel.class);
        displayView(0);
        loadWeddingProductList();
        loadNewBornList();
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
