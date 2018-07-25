package com.netset.believeapp.Fragment.musicSection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by netset on 22/1/18.
 */

public class SongsFragment extends BaseFragment implements ApiResponse {

    @BindView(R.id.label_songs)
    TextView labelSongs;
    @BindView(R.id.albumList_RV)
    RecyclerView songsList_RV;
    Unbinder unbinder;
    @BindView(R.id.label_lay)
    RelativeLayout labelLay;

    SongsAdapter songsAdapter;
    Call<JsonObject> GetSongs;
    RecentPlayListModel result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.music_album_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle b = getArguments();
        if(b.getString("From").equals("main")){
            GetSongsList();
            labelSongs.setText(getResources().getString(R.string.label_songs));
          //  ((HomeActivity) getActivity()).setToolbarTitle("Recently played", true, false, false, null);
        }else{
            labelSongs.setText(b.getString("name"));
            ((HomeActivity) getActivity()).setToolbarTitle(b.getString("name"), true, false, false, null);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("album_id", b.getString("id"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetSongs =  baseActivity.apiInterface.Get_SongsAlbums(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetSongs, this);
        }
    }

    public void GetSongsList() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetSongs =  baseActivity.apiInterface.Get_AllSongs(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetSongs, this);
    }


    @Override
    public void onSuccess(Call call, Object object) {
        Log.e("Response body", ">>>>>>>>>" + object.toString());
        if(call == GetSongs) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), RecentPlayListModel.class);
            songsAdapter = new SongsAdapter(baseActivity, result.data,"songs");
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(baseActivity);
            songsList_RV.setLayoutManager(layoutManager);
            songsList_RV.addItemDecoration(new SimpleDividerItemDecorationBlue(baseActivity));
            songsList_RV.setItemAnimator(new DefaultItemAnimator());
            songsList_RV.setAdapter(songsAdapter);

            songsList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, songsList_RV, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    startActivity(new Intent(baseActivity, MediaPlayerActivity.class).putExtra("from", "songs").putExtra("filepath", result.data.get(position).songFile).putExtra("title", result.data.get(position).songTitle).putExtra("position", "" + position)
                            .putExtra("artist",result.data.get(position).albumId.artist.artistName).putExtra("album",result.data.get(position).albumId.albumTitle));
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
