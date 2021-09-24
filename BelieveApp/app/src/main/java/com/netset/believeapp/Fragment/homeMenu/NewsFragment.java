package com.netset.believeapp.Fragment.homeMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.ArticleAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.NewsListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.google.android.gms.internal.zzahf.runOnUiThread;
import static com.netset.believeapp.Utils.Constants.SC_NEWS;

/**
 * Created by netset on 10/1/18.
 */

public class NewsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.newsList)
    RecyclerView NewsLV;
    @BindView(R.id.container)
    SwipeRefreshLayout  mSwipeRefreshLayout;
    @BindView(R.id.nodata_TV)
    TextView nodata;
    Unbinder unbinder;
    NewsListModel result;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> getNews;

    private ArticleAdapter mAdapter;
    private ProgressBar progressBar;
    private String urlString;
    //private String urlString = "https://www.androidauthority.com/feed";
   // private String urlString = "http://feeds.bbci.co.uk/news/world/rss.xml";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_NEWS, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        progressBar = view.findViewById(R.id.progressBar);
        CallApi();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onResume() {

        super.onResume();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }


    public void loadFeed() {

        if (!mSwipeRefreshLayout.isRefreshing())
            progressBar.setVisibility(View.VISIBLE);

        Parser parser = new Parser();
      //  parser.execute("http://feeds.bbci.co.uk/news/world/rss.xml");
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {
            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {
                //list is an Array List with all article's information
                //set the adapter to recycler view
                mAdapter = new ArticleAdapter(list, R.layout.row, getActivity());
                NewsLV.setAdapter(mAdapter);
                progressBar.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                nodata.setVisibility(View.GONE);

            }

            //what to do in case of error
            @Override
            public void onError() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        nodata.setVisibility(View.VISIBLE );
                        CommonDialogs.customToast(getActivity(),"Unable to load data.");
                        Log.i("Unable to load ", "articles");
                    }
                });
            }
        });
    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getNews = baseActivity.apiInterface.Get_News(map);
        baseActivity.apiHitAndHandle.makeApiCall(getNews, this,false);
    }

    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), NewsListModel.class);

        if (result.data != null) {
            urlString = result.data.newsUrl;
            NewsLV.setLayoutManager(new LinearLayoutManager(getActivity()));
            NewsLV.setItemAnimator(new DefaultItemAnimator());
            NewsLV.setHasFixedSize(true);

            mSwipeRefreshLayout.setColorSchemeResources(R.color.light_pink, R.color.dark_pink);
            mSwipeRefreshLayout.canChildScrollUp();
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    mAdapter.clearData();
                    mAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(true);
                    loadFeed();
                }
            });

            if (!isNetworkAvailable()) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.alert_message)
                        .setTitle(R.string.alert_title)
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_positive,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });

                AlertDialog alert = builder.create();
                alert.show();

            } else if (isNetworkAvailable()) {
                loadFeed();
            }
        }else{
            progressBar.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
