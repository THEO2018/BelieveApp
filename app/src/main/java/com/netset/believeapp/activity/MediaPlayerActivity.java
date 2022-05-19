package com.netset.believeapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.netset.believeapp.GsonModel.RecentPlayListModel;
import com.netset.believeapp.Model.VolumeModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.Constants;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.music.Audio;
import com.netset.believeapp.music.MediaPlayerService;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

import static android.os.Build.VERSION.SDK_INT;
import static com.netset.believeapp.R.drawable.ic_back;

public class MediaPlayerActivity extends BaseActivity implements ApiResponse, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, View.OnTouchListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private int mediaFileLengthInMilliseconds;
    public static final String Broadcast_PLAY_NEW_AUDIO = "com.netset.believeapp.activity.PlayNewAudio";
    @BindView(R.id.trackTitle_TV)
    TextView trackTitleTV;
    @BindView(R.id.tag_TV)
    TextView tagTV;
    @BindView(R.id.album_TV)
    TextView albumTV;
    @BindView(R.id.linearLayout7)
    LinearLayout linearLayout7;
    /* @BindView(R.id.volumeControl_RV)
     RecyclerView volumeControl_RV;*/
    @BindView(R.id.volUp_IV)
    ImageView volUpIV;
    @BindView(R.id.volDown_IV)
    ImageView volDownIV;
    @BindView(R.id.volIcon_Container)
    RelativeLayout volIconContainer;
    @BindView(R.id.songIcon_IV)
    ImageView songIconIV;
    @BindView(R.id.linearLayout8)
    RelativeLayout linearLayout8;
    @BindView(R.id.songSeekBar_SB)
    SeekBar songSeekBarSB;
    @BindView(R.id.playPrev_IV)
    ImageView playPrevIV;
    @BindView(R.id.playTrack_IV)
    ImageView playTrackIV;
    @BindView(R.id.playNext_IV)
    ImageView playNextIV;
    @BindView(R.id.musicContainer)
    RelativeLayout musicContainer;
    @BindView(R.id.mySeekBar)
    VerticalSeekBar volumeSeekbar;
    int position;
    Call<JsonObject> GetRecent, GetSongs;
    RecentPlayListModel result;

    private MediaPlayerService player;
    boolean serviceBound = false;
    ArrayList<Audio> audioList;
    private final Handler handler = new Handler();
    public BaseActivity baseActivity;
    public static Spinner selecter_SPN;
    int sysMaxVolume = 100;
    String MusicPosition = "0";
    List<VolumeModel> volumeList = new ArrayList<>();
    // public static OnPlayActionClick onPlayActionClick;

    String titleName, titleTrack, image, artist, album;
    AudioManager audioManager = null;

    int currentVolume;
    MediaPlayer mediaPlayer;
    public Toolbar toolbar;
    public TextView menu_IM;
    public TextView toolbar_title, actionText_TV;
    public ImageView title_IM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_screen);
        ButterKnife.bind(this);

        setUpToolbar();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        loadAudioList();
        initControls();
    }


    private void loadAudioList() {
        // loadAudio();
        Intent in = getIntent();
        if (in.getStringExtra("from").equals("recent")) {
            GetRecentList();
        } else {
            GetSongsList();
        }
    }


    private void initControls() {
        try {

            volumeSeekbar.setMax(100);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {

                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetRecentList() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(MediaPlayerActivity.this));
        GetRecent = apiInterface.Get_RecentList(map);
        apiHitAndHandle.makeApiCall(GetRecent, this, this);
    }

    public void GetSongsList() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(MediaPlayerActivity.this));
        GetSongs = apiInterface.Get_AllSongs(map);
        apiHitAndHandle.makeApiCall(GetSongs, this, this);
    }

    // void registerMediaPlayCallBack(ad)

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        divView = findViewById(R.id.divView);
        setSupportActionBar(toolbar);
        View logo = getLayoutInflater().inflate(R.layout.custom_view_toolbar_home, null);
        toolbar.addView(logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("");
        songSeekBarSB.setMax(99);
        songSeekBarSB.setOnTouchListener(this);
        setToolbarTitle(Constants.SC_MUSIC_PLAYER, true, false, false, null);
    }

    @SuppressLint("NewApi")
    public void setToolbarTitle(String title, boolean isBack, boolean isRightMenu, boolean isFromHome, final OnCreateProfileListener callback) {

        divView = findViewById(R.id.divView);
        menu_IM = toolbar.findViewById(R.id.menu_icon);
        toolbar_title = toolbar.findViewById(R.id.tv_toolbar_title);
        actionText_TV = toolbar.findViewById(R.id.actionNext_TV);
        title_IM = toolbar.findViewById(R.id.title_IM);
        selecter_SPN = toolbar.findViewById(R.id.selecter_SPN);

        selecter_SPN.setVisibility(View.GONE);
        if (toolbar.getVisibility() != View.VISIBLE) {
            toolbar.setVisibility(View.VISIBLE);
            divView.setVisibility(View.VISIBLE);
        }
        toolbar_title.setText(title);

        if (isBack) {
            actionText_TV.setVisibility(View.INVISIBLE);
            menu_IM.setText("");
            menu_IM.setBackground(getResources().getDrawable(ic_back));
        } else {
            if (!isFromHome) {
                actionText_TV.setVisibility(View.VISIBLE);
                menu_IM.setBackgroundResource(R.drawable.ic_menu);
                title_IM.setImageResource(0);
            } else {
                actionText_TV.setVisibility(View.INVISIBLE);
                menu_IM.setText("");
                menu_IM.setBackgroundResource(0);
                toolbar_title.setText("");
                title_IM.setImageResource(R.drawable.ic_believe_home);

            }
        }

        if (isRightMenu) {
            actionText_TV.setVisibility(View.VISIBLE);
            actionText_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCreateProfileResponse();
                }
            });
        } else {
            actionText_TV.setVisibility(View.INVISIBLE);
        }
        menu_IM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    private void primarySeekBarProgressUpdater() {
        songSeekBarSB.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaFileLengthInMilliseconds) * 100));// This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setVolumeList() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sysMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        if (volumeList.size() == 0) {

            for (int i = 0; i < sysMaxVolume; i++) {
                VolumeModel model = new VolumeModel();
                if (i <= currentVolume) {
                    model.setSelected(false);
                } else {
                    model.setSelected(true);
                }
                model.setPosition(i);
                volumeList.add(model);
            }

        }

    }


    private boolean checkAndRequestPermissions() {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            int permissionReadPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            List<String> listPermissionsNeeded = new ArrayList<>();

            if (permissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        String TAG = "LOG_PERMISSION";
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions

                    if (perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d(TAG, "Phone state and storage permissions granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                        loadAudioList();
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                      //shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                            showDialogOK("Phone state and storage permissions required for this app_icon",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app_icon.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //proceed with logic by disabling the related features or quit the app_icon.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("serviceStatus", serviceBound);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean("serviceStatus");
    }

    //Binding this Client to the AudioPlayer Service
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            player = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    /**
     * Load audio files using {@link ContentResolver}
     * <p>
     * If this don't works for you, load the audio files to audioList Array your oun way
     */
    private void loadAudio() {
        ContentResolver contentResolver = getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            audioList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                // Save to audioList
                audioList.add(new Audio(data, title, album, artist));
            }
        }
        if (cursor != null)
            cursor.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(serviceConnection);
            //service is active
            player.stopSelf();
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager audio = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            volumeSeekbar.setProgress(currentVolume);
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            volumeSeekbar.setProgress(currentVolume);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.volUp_IV, R.id.volDown_IV, R.id.playPrev_IV, R.id.playTrack_IV, R.id.playNext_IV})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.volUp_IV:

                if (audioManager != null) {
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                } else {
                    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                }
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                if (currentVolume <= 100) {
                    volumeSeekbar.setProgress(currentVolume + 1);
                }

                break;
            case R.id.volDown_IV:
                if (audioManager != null) {
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                } else {
                    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                }
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                //  Log.e("currentVolume", "currentVolume : " + currentVolume);

                if (currentVolume != 0) {
                    volumeSeekbar.setProgress(currentVolume - 1);
                }
                break;
            case R.id.playPrev_IV:

                if (position == 0) {

                    CommonDialogs.customToast(MediaPlayerActivity.this, "This is the first song of this playlist.");

                } else {


                    try {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                        position = (position - 1);
                        titleTrack = "" + result.data.get(position).songFile;
                        trackTitleTV.setText(result.data.get(position).songTitle);
                        CommonDialogs.getDisplayImage(MediaPlayerActivity.this, result.data.get(position).albumId.albumImage, songIconIV, "#ffffff");
                        mediaPlayer.setDataSource(titleTrack);// setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                        mediaPlayer.prepare();
                        //   mediaPlayer.start();// you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        playTrackIV.setImageResource(R.drawable.ic_pause);
                    } else {
                        mediaPlayer.pause();
                        playTrackIV.setImageResource(R.drawable.ic_play);
                    }


                }


                break;
            case R.id.playTrack_IV:
                try {
                    titleName = "" + result.data.get(position).songTitle;
                    titleTrack = "" + result.data.get(position).songFile;
                    mediaPlayer.setDataSource(titleTrack);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playTrackIV.setImageResource(R.drawable.ic_pause);
                } else {
                    mediaPlayer.pause();
                    playTrackIV.setImageResource(R.drawable.ic_play);
                }
                primarySeekBarProgressUpdater();


                break;
            case R.id.playNext_IV:

                if (position == result.data.size() - 1) {

                    CommonDialogs.customToast(MediaPlayerActivity.this, "This is the last song of this playlist.");

                } else {

                    try {

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                        position = (position + 1);
                        titleTrack = "" + result.data.get(position).songFile;
                        trackTitleTV.setText(result.data.get(position).songTitle);
                        CommonDialogs.getDisplayImage(MediaPlayerActivity.this, result.data.get(position).albumId.albumImage, songIconIV, "#ffffff");
                        mediaPlayer.setDataSource(titleTrack); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                        mediaPlayer.prepare();
                        //   mediaPlayer.start();// you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                        playTrackIV.setImageResource(R.drawable.ic_pause);
                    } else {
                        mediaPlayer.pause();
                        playTrackIV.setImageResource(R.drawable.ic_play);
                    }
                    primarySeekBarProgressUpdater();

                }
                break;

        }
    }


    @Override
    public void onBackPressed() {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        MediaPlayerActivity.this.finish();
    }


    @Override
    public void onSuccess(Call call, Object object) {
        if (call == GetRecent) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), RecentPlayListModel.class);

            Intent in = getIntent();
            if (in.getStringExtra("from").equals("recent")) {
                position = Integer.parseInt(in.getStringExtra("position"));
                MusicPosition = "" + result.data.get(position);

                titleName = result.data.get(position).songTitle;
                titleTrack = result.data.get(position).songFile;
                artist = result.data.get(position).albumId.artist.artistName;
                album = result.data.get(position).albumId.albumTitle;
                image = result.data.get(position).albumId.albumImage;
            }
            tagTV.setText(artist);
            albumTV.setText(album);
            trackTitleTV.setText(titleName);
            CommonDialogs.getDisplayImage2(MediaPlayerActivity.this, image, songIconIV, "#ffffff");
            audioList = new ArrayList<>();
            for (int i = 0; i < result.data.size(); i++) {
                audioList.add(new Audio(result.data.get(i).songFile, result.data.get(i).songTitle, result.data.get(i).albumId.albumImage));
            }


            try {
                mediaPlayer.setDataSource(titleTrack);
                mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                playTrackIV.setImageResource(R.drawable.ic_pause);
            } else {
                mediaPlayer.pause();
                playTrackIV.setImageResource(R.drawable.ic_play);
            }

            primarySeekBarProgressUpdater();


        } else if (call == GetSongs) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), RecentPlayListModel.class);


            Intent in = getIntent();
            if (in.getStringExtra("from").equals("songs")) {
                position = Integer.parseInt(in.getStringExtra("position"));
                MusicPosition = "" + result.data.get(position);
                titleName = "" + result.data.get(position).songTitle;
                titleTrack = "" + result.data.get(position).songFile;
                artist = "" + result.data.get(position).albumId.artist.artistName;
                album = "" + result.data.get(position).albumId.albumTitle;
                image = "" + result.data.get(position).albumId.albumImage;
            }
            trackTitleTV.setText(titleName);
            tagTV.setText(artist);
            albumTV.setText(album);
            CommonDialogs.getDisplayImage2(MediaPlayerActivity.this, image, songIconIV, "#ffffff");
            audioList = new ArrayList<>();
            for (int i = 0; i < result.data.size(); i++) {
                audioList.add(new Audio(result.data.get(i).songFile, result.data.get(i).songTitle, result.data.get(i).albumId.albumImage));
            }

            try {
                mediaPlayer.setDataSource(titleTrack); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                playTrackIV.setImageResource(R.drawable.ic_pause);
            } else {
                mediaPlayer.pause();
                playTrackIV.setImageResource(R.drawable.ic_play);
            }

            primarySeekBarProgressUpdater();
        }
    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (mediaPlayer.isPlaying()) {
            SeekBar sb = (SeekBar) view;
            int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
            mediaPlayer.seekTo(playPositionInMillisecconds);
        }
        return false;
    }
}
