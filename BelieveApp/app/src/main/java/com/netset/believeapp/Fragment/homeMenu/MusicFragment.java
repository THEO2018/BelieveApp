package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.Fragment.musicSection.AlbumsFragment;
import com.netset.believeapp.Fragment.musicSection.LibraryFragment;
import com.netset.believeapp.Fragment.musicSection.RecentAddedListFragment;
import com.netset.believeapp.Fragment.musicSection.SongsFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.netset.believeapp.Utils.Constants.SC_MUSIC;

/**
 * Created by netset on 16/1/18.
 */

public class MusicFragment extends BaseFragment {

    @BindView(R.id.recent_IV)
    ImageView recentIV;
    @BindView(R.id.recent_TV)
    TextView recentTV;
    @BindView(R.id.recentView)
    LinearLayout libraryView;
    @BindView(R.id.album_IV)
    ImageView albumIV;
    @BindView(R.id.album_TV)
    TextView albumTV;
    @BindView(R.id.albumView)
    LinearLayout albumView;
    @BindView(R.id.songs_IV)
    ImageView songsIV;
    @BindView(R.id.songs_TV)
    TextView songsTV;
    @BindView(R.id.songsView)
    LinearLayout songsView;
    @BindView(R.id.selectionTab)
    LinearLayout selectionTab;
    @BindView(R.id.artistView)
    LinearLayout artistView;
    @BindView(R.id.artist_IV)
    ImageView artistIV;
    @BindView(R.id.artist_TV)
    TextView artistTV;
    Unbinder unbinder;

    int selectedTabPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.music_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_MUSIC, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayView(selectedTabPosition);
    }

    @OnClick({R.id.recentView, R.id.albumView, R.id.songsView,R.id.artistView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recentView:
                displayView(0);
                break;
            case R.id.albumView:
                displayView(1);
                break;
            case R.id.songsView:
                displayView(2);
                break;
            case R.id.artistView:
                displayView(3);
                break;
        }
    }

    private void displayView(int position) {
        if (position == 0) {
            recentIV.setImageResource(R.drawable.ic_library);
            recentTV.setTextColor(getResources().getColor(R.color.white));

            albumIV.setImageResource(R.drawable.ic_album_un);
            albumTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            songsIV.setImageResource(R.drawable.ic_music_un);
            songsTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            artistIV.setImageResource(R.drawable.ic_artist_un);
            artistTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

        } else if (position == 1) {
            albumIV.setImageResource(R.drawable.ic_album);
            albumTV.setTextColor(getResources().getColor(R.color.white));

            recentIV.setImageResource(R.drawable.ic_library_un);
            recentTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            songsIV.setImageResource(R.drawable.ic_music_un);
            songsTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            artistIV.setImageResource(R.drawable.ic_artist_un);
            artistTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

        } else if (position == 2) {
            songsIV.setImageResource(R.drawable.ic_music_small);
            songsTV.setTextColor(getResources().getColor(R.color.white));

            recentIV.setImageResource(R.drawable.ic_library_un);
            recentTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            albumIV.setImageResource(R.drawable.ic_album_un);
            albumTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            artistIV.setImageResource(R.drawable.ic_artist_un);
            artistTV.setTextColor(getResources().getColor(R.color.unselected_item_color));
        }

       else if (position == 3) {
            recentIV.setImageResource(R.drawable.ic_library_un);
            recentTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            albumIV.setImageResource(R.drawable.ic_album_un);
            albumTV.setTextColor(getResources().getColor(R.color.unselected_item_color));

            songsIV.setImageResource(R.drawable.ic_music_un);
            songsTV.setTextColor(getResources().getColor(R.color.unselected_item_color));


            artistIV.setImageResource(R.drawable.ic_artist);
            artistTV.setTextColor(getResources().getColor(R.color.white));


        }


        showFragment(position);
    }

    private void showFragment(int position) {
        selectedTabPosition = position;
        switch (position) {
            case 0:

                baseActivity.navigateFragment_NoBackStack(R.id.musicContainer, new LibraryFragment());
//                startActivity(new Intent(baseActivity, MediaPlayerActivity.class));

                break;
            case 1:
                Bundle args = new Bundle();
                args.putString("From","main");
                baseActivity.navigateFragmentNoBackStack_ARG(R.id.musicContainer, new AlbumsFragment(),args);

                break;
            case 2:

                Bundle args1 = new Bundle();
                args1.putString("From","main");
                baseActivity.navigateFragmentNoBackStack_ARG(R.id.musicContainer, new SongsFragment(),args1);

                break;

                case 3:

                    Bundle args2 = new Bundle();
                    args2.putString("From","artist");
                    baseActivity.navigateFragmentNoBackStack_ARG(R.id.musicContainer, new RecentAddedListFragment(),args2);
                    break;

        }
    }
}
