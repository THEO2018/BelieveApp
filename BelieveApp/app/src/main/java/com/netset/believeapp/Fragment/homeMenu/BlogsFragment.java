package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.BlogsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.blogSection.BlogDetailFragment;
import com.netset.believeapp.GsonModel.BlogsListModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_BLOGS;

/**
 * Created by netset on 9/1/18.
 */

public class BlogsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.blogsList_RV)
    RecyclerView blogsList_RV;
    Unbinder unbinder;
    BlogsAdapter blogsAdapter;
    private TextView txtNodata;
    Call<JsonObject> getBlogs;
    BlogsListModel result;
    List<BlogsModel> blogList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.blogs_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_BLOGS, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNodata = (TextView) view.findViewById(R.id.txt_nodata);
        CallApi();
      //  setBlogAdapter();
    }


    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        getBlogs = baseActivity.apiInterface.GetAllBlogs(map);
        baseActivity.apiHitAndHandle.makeApiCall(getBlogs, this);
    }


    @Override
    public void onSuccess(Call call, Object object) {


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), BlogsListModel.class);

        blogsAdapter = new BlogsAdapter(baseActivity, result.getData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
        blogsList_RV.setLayoutManager(mLayoutManager);
        blogsList_RV.setItemAnimator(new DefaultItemAnimator());
        blogsList_RV.setAdapter(blogsAdapter);
        if(result.getData().size() ==0 ){
            txtNodata.setVisibility(View.VISIBLE);
        }else{
            txtNodata.setVisibility(View.GONE);
        }
        blogsList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, blogsList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle args = new Bundle();
                args.putString("blogid",result.getData().get(position).getId());
                baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new BlogDetailFragment(),args);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
