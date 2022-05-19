package com.netset.believeapp.Fragment.bibleFrag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.StoreListAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.PdfViewerFragment;
import com.netset.believeapp.Fragment.homeMenu.WebViewFragment;
import com.netset.believeapp.GsonModel.StoreListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.bibleModel.BibleModel;
import com.netset.believeapp.bibleModel.BibleModelItem;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_STORE;

public class BibleFragStore extends BaseFragment implements ApiResponse{

    @BindView(R.id.newsList)
    ListView NewsLV;
    @BindView(R.id.nodata_TV)
    TextView noDataText;
    Unbinder unbinder;
    Call<JsonObject> getBible;
    List<BibleModelItem> biblelist = new ArrayList<>();
    BibleModel bibleData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storelist, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle("Online Bible", false, false, false, null);
        CallApi();
        return rootView;
    }



    public void CallApi() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        assert baseActivity != null;
        getBible = baseActivity.apiInterface.Get_Bible(map);
        baseActivity.apiHitAndHandle.makeApiCall(getBible, this, baseActivity);

    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        if (!biblelist.isEmpty()){
            biblelist.clear();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        bibleData = gson.fromJson(object.toString(), BibleModel.class);
        biblelist.addAll(Objects.requireNonNull(bibleData.getData()));
        BibleListAdapter adapter = new BibleListAdapter(baseActivity,biblelist);
        NewsLV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        NewsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Objects.requireNonNull(biblelist.get(i).getBible_url()).contains("pdf")){
                    assert baseActivity != null;
                    baseActivity.navigateFragmentTransaction(R.id.homeContainer, new PdfViewerFragment(Objects.requireNonNull(biblelist.get(i).getBible_url()), Objects.requireNonNull(biblelist.get(i).getTitle())));
                }else if (Objects.requireNonNull(biblelist.get(i).getBible_url()).contains("http://")
                        || Objects.requireNonNull(biblelist.get(i).getBible_url()).contains("https://")
                        || Objects.requireNonNull(biblelist.get(i).getBible_url()).contains("www")){
                    Bundle args = new Bundle();
                    args.putString("newslink",biblelist.get(i).getBible_url());
                    args.putString("newsname",biblelist.get(i).getTitle());
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);

                }

            }
        });

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }

}
