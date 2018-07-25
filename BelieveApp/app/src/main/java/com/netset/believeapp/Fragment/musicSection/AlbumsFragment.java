package com.netset.believeapp.Fragment.musicSection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.AlbumsAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.AlbumModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.Utils.recyclerCustomisation.RecyclerTouchListener;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by netset on 22/1/18.
 */

public class AlbumsFragment extends BaseFragment implements ApiResponse {
    @BindView(R.id.albumList_RV)
    RecyclerView albumList_RV;
    @BindView(R.id.nodata_TV)
            TextView NodataText;
    Unbinder unbinder;

    AlbumsAdapter albumsAdapter;

    @BindView(R.id.label_songs)
    TextView labelSongs;

    Call<JsonObject> GetRecent,GetArtistAlbum;
    AlbumModel result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.music_album_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        labelSongs.setText(getResources().getString(R.string.label_albums));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GetAlbum();
    }

    public void GetAlbum() {

        Bundle b = getArguments();
        if(b.getString("From").equals("main")){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetRecent = baseActivity.apiInterface.Get_MusicAlbums(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetRecent, this);
        }else{
            ((HomeActivity) getActivity()).setToolbarTitle(b.getString("name"), true, false, false, null);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("artist_id",b.getString("id"));
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            GetArtistAlbum = baseActivity.apiInterface.Get_ArtistsAlbum(map);
            baseActivity.apiHitAndHandle.makeApiCall(GetArtistAlbum, this);
        }

    }



    @Override
    public void onSuccess(Call call, Object object) {



        if(call == GetRecent) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), AlbumModel.class);

            if(result.data.size()==0){
                NodataText.setText("No album found");
                NodataText.setVisibility(View.VISIBLE);
            }else{
                NodataText.setVisibility(View.GONE);
            }


            albumsAdapter = new AlbumsAdapter(baseActivity, result.data);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(baseActivity, 2);
            albumList_RV.setLayoutManager(layoutManager);
            albumList_RV.setItemAnimator(new DefaultItemAnimator());
            albumList_RV.setAdapter(albumsAdapter);


            albumList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, albumList_RV, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle args = new Bundle();
                    args.putString("From", "album");
                    args.putString("id", result.data.get(position).id);
                    args.putString("name", result.data.get(position).albumTitle);
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SongsFragment(), args);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

        else if(call == GetArtistAlbum){

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), AlbumModel.class);

            albumsAdapter = new AlbumsAdapter(baseActivity, result.data);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(baseActivity, 2);
            albumList_RV.setLayoutManager(layoutManager);
            albumList_RV.setItemAnimator(new DefaultItemAnimator());
            albumList_RV.setAdapter(albumsAdapter);


            albumList_RV.addOnItemTouchListener(new RecyclerTouchListener(baseActivity, albumList_RV, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle args = new Bundle();
                    args.putString("From", "album");
                    args.putString("id", result.data.get(position).id);
                    args.putString("name", result.data.get(position).albumTitle);
                    baseActivity.navigateFragmentTransaction_ARG(R.id.homeContainer, new SongsFragment(), args);
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
