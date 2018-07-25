package com.netset.believeapp.Fragment.communitySection;

import android.os.Bundle;
import android.os.Handler;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.CommentAdapter;
import com.netset.believeapp.Adapter.PollPercentAdapter;
import com.netset.believeapp.Adapter.PollsOptionListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.birthdaySection.SelectedUserProfileFragment;
import com.netset.believeapp.GsonModel.PollsDetailModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 31/1/18.
 */

public class ShowPollFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.txtChooseOption)
    TextView mChooseOption;
    @BindView(R.id.commentHeader)
    RelativeLayout mCommentHeader;
    @BindView(R.id.comntView)
    RelativeLayout mCommnetSection;
    @BindView(R.id.pollImage_IM)
    ImageView pollImageIM;
    @BindView(R.id.pollTitle_TV)
    TextView pollTitleTV;
    @BindView(R.id.pollTime_TV)
    TextView pollTimeTV;
    @BindView(R.id.msgText_TV)
    TextView msgTextTV;
    @BindView(R.id.timerContainer)
    LinearLayout timerContainer;
    @BindView(R.id.optionList_RV)
    RecyclerView optionList_RV;
    @BindView(R.id.percentList_RV)
    RecyclerView percentList_RV;
    @BindView(R.id.viewAllCmnt_TV)
    TextView viewAllCmntTV;
    @BindView(R.id.commentList_RV)
    RecyclerView commentList_RV;
    @BindView(R.id.txt1_hr)
    TextView txt1Hr;
    @BindView(R.id.txt2_hr)
    TextView txt2Hr;
    @BindView(R.id.txt1_min)
    TextView txt1Min;
    @BindView(R.id.txt2_min)
    TextView txt2Min;
    @BindView(R.id.txt1_sec)
    TextView txt1Sec;
    @BindView(R.id.txt2_sec)
    TextView txt2Sec;
    @BindView(R.id.send_IV)
    ImageView sendIV;
    @BindView(R.id.comment_ET)
    EditText comment_ET;
    Unbinder unbinder;

    PollPercentAdapter pollPercentAdapter;
    PollsOptionListAdapter pollsOptionListAdapter;
    CommentAdapter commentAdapter;

    Call<JsonObject> GetPolldetail, AddComment, AddVote;
    PollsDetailModel result;
    int seletedPosition = -1;
    View rootView;
    java.util.Date eventDate = new java.util.Date();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if(rootView == null){

            rootView = inflater.inflate(R.layout.show_poll_fragment, null);
            unbinder = ButterKnife.bind(this, rootView);
            ((HomeActivity) getActivity()).setToolbarTitle("Poll Name", true, false, false, null);
            GetPollDetail();
            viewAllComntListSpannable();
        }
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // setPollList();

    }

    public void GetPollDetail() {
        Bundle b = getArguments();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("poll_id", b.getString("id"));
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetPolldetail = baseActivity.apiInterface.Get_PollDetail(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetPolldetail, this);
    }


    @OnClick({R.id.send_IV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_IV:

                if (comment_ET.getText().toString().trim().equals("")) {

                } else {
                    Bundle b = getArguments();
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("poll_id", b.getString("id"));
                    map.put("comment_msg", comment_ET.getText().toString().trim());
                    map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                    AddComment = baseActivity.apiInterface.add_CommentPoll(map);
                    baseActivity.apiHitAndHandle.makeApiCall(AddComment, this, false);
                }
                break;

        }
    }

    private void setPollPercentList() {
        pollPercentAdapter = new PollPercentAdapter(baseActivity, result.data.options);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        percentList_RV.setLayoutManager(mLayoutManager);
        percentList_RV.setItemAnimator(new DefaultItemAnimator());
        percentList_RV.setAdapter(pollPercentAdapter);

    }



    private void setOptionList() {
        pollsOptionListAdapter = new PollsOptionListAdapter(baseActivity, result.data.options);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        optionList_RV.setLayoutManager(mLayoutManager);
        optionList_RV.setItemAnimator(new DefaultItemAnimator());
        optionList_RV.setAdapter(pollsOptionListAdapter);
        optionList_RV.setNestedScrollingEnabled(false);

        pollPercentAdapter = new PollPercentAdapter(baseActivity, result.data.options);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(baseActivity);//, LinearLayoutManager.HORIZONTAL, false);
        percentList_RV.setLayoutManager(mLayoutManager2);
        percentList_RV.setItemAnimator(new DefaultItemAnimator());
        percentList_RV.setAdapter(pollPercentAdapter);
        percentList_RV.setNestedScrollingEnabled(false);
    }

    private void setCommentAdapter() {
        commentAdapter = new CommentAdapter(baseActivity, result.data.comments, false,"poll");
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
                        args.putString("userId",result.data.comments.get(position).userId.id);
                        args.putString("from","poll");
                        baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SelectedUserProfileFragment(),args);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

    }

    private void viewAllComntListSpannable() {
        String viewAll_TEXT = getResources().getString(R.string.label_view_all);
        SpannableString content = new SpannableString(viewAll_TEXT);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        content.setSpan(clickableSpan_Comment_ViewAll, 0, content.length(), 0);
        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, content.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        viewAllCmntTV.setMovementMethod(LinkMovementMethod.getInstance());
        viewAllCmntTV.setText(content);

    }

    ClickableSpan clickableSpan_Comment_ViewAll = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            Bundle b = getArguments();
            Bundle args = new Bundle();
            args.putString("pollId",b.getString("id"));
            args.putString("title","poll");
            baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new ShowPollCommentsFragment(),args);
        }
    };


    @Override
    public void onSuccess(Call call, Object object) {

        if (call == GetPolldetail) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), PollsDetailModel.class);


            CommonDialogs.getDisplayImage(getActivity(), result.data.pollImage, pollImageIM, "#d3d3d3");
            pollTitleTV.setText(result.data.pollTitle);
            pollTimeTV.setText(result.data.timeAgo);
            msgTextTV.setText(result.data.question);
            if (result.data.comments.size() == 0) {
                mCommentHeader.setVisibility(View.GONE);
            } else {

                mCommentHeader.setVisibility(View.VISIBLE);
            }

            boolean isClosed = false;

            try {
                isClosed = getArguments().getBoolean("isClosed", false);
            } catch (NullPointerException e) {
                // Avoid it..
            }
            if (isClosed) {
                // Hide Choose option

                timerContainer.setVisibility(View.GONE);
                mChooseOption.setVisibility(View.GONE);
                // Hide option list
                optionList_RV.setVisibility(View.VISIBLE);
                // Hide percent list

                if(result.data.settingHideResultsAfterVoting.equals(true)){
                    percentList_RV.setVisibility(View.VISIBLE);
                }else{
                    percentList_RV.setVisibility(View.GONE);
                }

                // hide comment list
                commentList_RV.setVisibility(View.VISIBLE);
                // hide comment header view
                mCommentHeader.setVisibility(View.VISIBLE);
                // hide send comment section
                mCommnetSection.setVisibility(View.GONE);

                if (result.data.comments.size() == 0) {
                    mCommentHeader.setVisibility(View.GONE);
                } else {

                    mCommentHeader.setVisibility(View.VISIBLE);
                }

            }else{
                countDownStart(result.data.endTime);
                optionList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, optionList_RV, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        if (result.data.settingMultipleChoice.equals(false)) {

                            for (int i = 0; i < result.data.options.size(); i++) {
                                if (result.data.options.get(position).myVotePoll.equals(true)) {
                                    seletedPosition=position;
                                }
                            }
                            if (seletedPosition == -1) {
                                seletedPosition = position;
                                Bundle b = getArguments();
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("poll_id", b.getString("id"));
                                map.put("poll_option_id", result.data.options.get(position).id);
                                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                                AddVote = baseActivity.apiInterface.add_VotePoll(map);
                                baseActivity.apiHitAndHandle.makeApiCall(AddVote, ShowPollFragment.this, false);
                            }


                        } else {
                            if (result.data.options.get(position).myVotePoll.equals(true)) {

                            } else {
                                Bundle b = getArguments();
                                HashMap<String, String> map = new HashMap<String, String>();
                                map.put("poll_id", b.getString("id"));
                                map.put("poll_option_id", result.data.options.get(position).id);
                                map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
                                AddVote = baseActivity.apiInterface.add_VotePoll(map);
                                baseActivity.apiHitAndHandle.makeApiCall(AddVote, ShowPollFragment.this, false);
                            }
                        }

                        //   pollsOptionListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
            }

            setOptionList();
           // setPollPercentList();

            setCommentAdapter();


        } else if (call == AddComment) {
            comment_ET.setText("");
            CommonDialogs.hideSoftKeyboard(getActivity());
            GetPollDetail();
        } else if (call == AddVote) {
            GetPollDetail();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    public void countDownStart(final String endtime) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    // date = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).parse(string.replaceAll("Z$", "+0000"));
                    // Here Set your Event Date java.util.Date eventDate = null;wewq

                    eventDate = dateFormat.parse(endtime.replaceAll("Z$", "+0000"));


                    java.util.Date currentDate = new java.util.Date();
                    if (!currentDate.after(eventDate)) {
                        long diff = eventDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;

                        String hr = "" + hours;
                        String min = "" + minutes;
                        String sec = "" + seconds;


                        txt1Hr.setText(hr.substring(0, 1));
                        try {
                            txt2Hr.setText(hr.substring(1, 2));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        txt1Min.setText(min.substring(0, 1));
                        txt2Min.setText(min.substring(1, 2));
                        txt1Sec.setText(sec.substring(0, 1));
                        txt2Sec.setText(sec.substring(1, 2));



                    } else {

                        txt1Hr.setText("0");
                        txt2Hr.setText("0");
                        txt1Min.setText("0");
                        txt2Min.setText("0");
                        txt1Sec.setText("0");
                        txt2Sec.setText("0");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }
}
