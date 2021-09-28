package com.netset.believeapp.Fragment.wallSection;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.HomeActivity;
import com.squareup.picasso.Picasso;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.utils.PathUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.netset.believeapp.Utils.Constants.SC_CREATE_POST;

/**
 * Created by netset on 30/1/18.
 */

public class PostStatusFragment extends BaseFragment {
    @BindView(R.id.profile_image_IV)
    CircleImageView profileImageIV;
    @BindView(R.id.usrName_TV)
    TextView usrNameTV;
    @BindView(R.id.profileContainer)
    LinearLayout profileContainer;
    @BindView(R.id.status_ET)
    EditText statusET;
    @BindView(R.id.status_Container)
    LinearLayout statusContainer;
    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.uploadPhoto_TV)
    AppCompatTextView uploadPhotoTV;
    @BindView(R.id.uploadVideo_TV)
    AppCompatTextView uploadVideoTV;
    @BindView(R.id.button)
    AppCompatButton button;
    @BindView(R.id.actionView)
    RelativeLayout actionView;
    Unbinder unbinder;



    @BindView(R.id.statusPic_IV)
    ImageView statusPicIV;
    @BindView(R.id.imgList_RV)
    RecyclerView imgListRV;
    @BindView(R.id.videoICon_IV)
    ImageView videoIConIV;
    @BindView(R.id.singleIMG_Container)
    RelativeLayout singleIMGContainer;
    private File destination;

    public static final int REQUEST_CODE_CHOOSE = 100;

    File videoFile, profileImage;
    List<Uri> mSelected;
    public String selectedFilePath = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_post_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_CREATE_POST, true, false, false, null);
        return rootView;
    }



    @OnClick({R.id.uploadPhoto_TV, R.id.uploadVideo_TV, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uploadPhoto_TV:
                selectMedia();
                break;
            case R.id.uploadVideo_TV:
                selectMedia();
                break;
            case R.id.button:
                break;
        }
    }

    private void selectMedia() {
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .countable(true).capture(true)
                .captureStrategy(new CaptureStrategy(true, "com.netset.believeapp"))
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen._120dp))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new PicassoEngine()).forResult(REQUEST_CODE_CHOOSE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            sendBackImagePath(mSelected.get(0));
        }
    }

    void sendBackImagePath(Uri inputUri) {
        selectedFilePath = PathUtils.getPath(baseActivity, inputUri);
        if (inputUri.toString().contains("video") || inputUri.toString().contains("Movies") || inputUri.toString().contains("movies")) {
            profileImage = null;
            videoFile = new File(selectedFilePath);
            Bitmap bmp = customCreatThumbnail(selectedFilePath);
            statusPicIV.setImageBitmap(bmp);
            Log.e("VIDEOURI", "" + inputUri);

            videoIConIV.setVisibility(View.VISIBLE);
        } else {
            videoFile = null;
            profileImage = new File(selectedFilePath);
            Picasso.with(baseActivity).load(profileImage).into(statusPicIV);
            videoIConIV.setVisibility(View.GONE);

        }
    }

    private Bitmap customCreatThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime(-1);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        if (bitmap == null)
            return null;

        return bitmap;
    }

}
