package com.netset.believeapp.Fragment.musicSection;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.SongsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.ArtistModel;
import com.netset.believeapp.GsonModel.RecentPlayListModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.Utils.recyclerCustomisation.SimpleDividerItemDecorationBlue;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.activity.MediaPlayerActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 4/5/18.
 */

public class RecentAddedListFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.weddingList_RV)
    RecyclerView recentList_RV;
    @BindView(R.id.main_lay)
    RelativeLayout mainLay;
    @BindView(R.id.lable_TV)
    TextView lableTV;
    @BindView(R.id.nodata_TV)
    TextView NodataText;
    Unbinder unbinder;


    Call<JsonObject> GetRecent,Getartists;
    RecentPlayListModel result;
    ArtistModel artistModel;
    SongsAdapter songsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wedding_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLay.setBackgroundResource(R.drawable.shape_pink_outline_solid);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetRecentList();
    }

    public void GetRecentList() {

        Bundle b = getArguments();
        if(b.getString("From").equals("recent")){
            lableTV.setVisibility(View.GONE);
            ((HomeActivity) getActivity()).setToolbarTitle("Recently Added", true, false, false, null);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetRecent = baseActivity.apiInterface.Get_RecentList(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetRecent, this);
        }else{
            lableTV.setVisibility(View.VISIBLE);
            lableTV.setText("Artists");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            Getartists = baseActivity.apiInterface.Get_Artists(map);
            baseActivity.apiHitAndHandle.makeApiCall(Getartists, this);

        }

    }

    @Override
    public void onSuccess(Call call, Object object) {


        if(call == GetRecent){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        result = gson.fromJson(object.toString(), RecentPlayListModel.class);

        if(result.data.size()==0){
            NodataText.setText("No song found");
            NodataText.setVisibility(View.VISIBLE);
        }else{
            NodataText.setVisibility(View.GONE);
        }

        songsAdapter = new SongsAdapter(baseActivity, result.data,"songs");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(baseActivity);
        recentList_RV.setLayoutManager(layoutManager);
        recentList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
        recentList_RV.setItemAnimator(new DefaultItemAnimator());
        recentList_RV.setAdapter(songsAdapter);


        recentList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, recentList_RV, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(baseActivity, MediaPlayerActivity.class).putExtra("from","recent").putExtra("filepath",result.data.get(position).songFile).putExtra("title",result.data.get(position).songTitle).putExtra("position",""+position)
                        .putExtra("artist",result.data.get(position).albumId.artist.artistName).putExtra("album",result.data.get(position).albumId.albumTitle));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        }


        else if(call ==  Getartists){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            artistModel = gson.fromJson(object.toString(), ArtistModel.class);


            if(artistModel.data.size()==0){
                NodataText.setText("No artist found");
                NodataText.setVisibility(View.VISIBLE);
            }else{
                NodataText.setVisibility(View.GONE);
            }

            songsAdapter = new SongsAdapter(baseActivity, artistModel.data,"artist","");
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(baseActivity);
            recentList_RV.setLayoutManager(layoutManager);
            recentList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
            recentList_RV.setItemAnimator(new DefaultItemAnimator());
            recentList_RV.setAdapter(songsAdapter);



            recentList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, recentList_RV, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle args = new Bundle();
                    args.putString("From","artist");
                    args.putString("id",artistModel.data.get(position).id);
                    args.putString("name",artistModel.data.get(position).artistName);
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new AlbumsFragment(),args);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


        }





    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }
}
