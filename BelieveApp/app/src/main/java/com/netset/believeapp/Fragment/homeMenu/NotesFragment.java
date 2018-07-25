package com.netset.believeapp.Fragment.homeMenu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.netset.believeapp.Adapter.NotesAdapter;
import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.GsonModel.NotesListModel;
import com.netset.believeapp.Model.BlogsModel;
import com.netset.believeapp.R;
import com.netset.believeapp.Utils.CommonDialogs;
import com.netset.believeapp.Utils.GeneralValues;
import com.netset.believeapp.activity.HomeActivity;
import com.netset.believeapp.retrofitManager.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

import static com.netset.believeapp.Utils.Constants.SC_NOTES;

/**
 * Created by netset on 10/1/18.
 */

public class NotesFragment extends BaseFragment implements ApiResponse {

    NotesAdapter notesAdapter;

    List<BlogsModel> blogList = new ArrayList<>();
    @BindView(R.id.divView)
    View divView;
    @BindView(R.id.notesList_RV)
    RecyclerView notesList_RV;
    Unbinder unbinder;
    @BindView(R.id.addNote_IV)
    AppCompatImageView addNoteIV;
    @BindView(R.id.txt_nodata)
            TextView txtNodata;
    AlertDialog.Builder dialogBuilder;
    AlertDialog b;
    NotesListModel result;
    /*ApiInterface apiInterface;
    ApiHitAndHandle apiHitAndHandle;*/
    Call<JsonObject> GetNotes,AddNotes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notes_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);
        ((HomeActivity) getActivity()).setToolbarTitle(SC_NOTES, false, false, false, null);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiHitAndHandle = ApiHitAndHandle.getInstance(getActivity());*/
        CallApi(true);
    }

    public void CallApi(boolean isProgress) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
        GetNotes = baseActivity.apiInterface.Get_Notes(map);
        baseActivity.apiHitAndHandle.makeApiCall(GetNotes, this,isProgress);

    }



    @OnClick(R.id.addNote_IV)
    public void onClick() {
        showAddNoteDialog();
    }


    /**
     * Custom Alert Dialog for Adding New Note ...
     */
    private void showAddNoteDialog() {

         dialogBuilder = new AlertDialog.Builder(baseActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_note_view, null);
        dialogBuilder.setView(dialogView);


        final EditText title_ET = dialogView.findViewById(R.id.title_ET);
        final EditText message_ET = dialogView.findViewById(R.id.message_ET);
        Button addNotes_BTN = dialogView.findViewById(R.id.addNote_BTN);
        TextView cancel_TV = dialogView.findViewById(R.id.cancel_TV);
        ImageView dismiss_IV = dialogView.findViewById(R.id.dismiss_IV);
        title_ET.setFilters(new InputFilter[]{EMOJI_FILTER});
        b = dialogBuilder.create();
        b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        b.setCancelable(false);

        addNotes_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = title_ET.getText().toString().trim();
                String message = message_ET.getText().toString().trim();

                validateNotesData(title, message);

            }
        });

        cancel_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });

        dismiss_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });


        b.show();
    }

    private void validateNotesData(String title, String message) {
        if (isValidText(title) && isValidText(message)) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("access_token", GeneralValues.get_Access_Key(getActivity()));
            map.put("title", title);
            map.put("description", message);
            AddNotes = baseActivity.apiInterface.Add_Note(map);
            baseActivity.apiHitAndHandle.makeApiCall(AddNotes, this);


        } else {

            if (!isValidText(title)) {
                CommonDialogs.customToast(getActivity(),"Please Enter Title");
            } else if (!isValidText(message)) {
                CommonDialogs.customToast(getActivity(),"Please Enter Description");
            }else if(!isValidTitle(title)){
                CommonDialogs.customToast(getActivity(),"Please add at least one alphabet in the title");
            }
        }
    }

    public boolean isValidTitle(String title) {
        String TITLE_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])$";
        Pattern pattern = Pattern.compile(TITLE_PATTERN);
        Matcher matcher = pattern.matcher(title);
        return matcher.matches();
    }

    @Override
    public void onSuccess(Call call, Object object) {

        Log.e("Response body", ">>>>>>>>>" + object.toString());

        if(call == GetNotes) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            result = gson.fromJson(object.toString(), NotesListModel.class);

            if(result.getData().size() == 0){
                txtNodata.setVisibility(View.VISIBLE);
            }else{
                txtNodata.setVisibility(View.GONE);
            }

            notesAdapter = new NotesAdapter(baseActivity, result.getData());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(baseActivity);
            notesList_RV.setLayoutManager(mLayoutManager);
            notesList_RV.setItemAnimator(new DefaultItemAnimator());
            notesList_RV.setAdapter(notesAdapter);
        }
        else if(call == AddNotes){
            b.dismiss();
            CommonDialogs.customToast(getActivity(),result.getMessage());
            CallApi(false);
        }

    }

    @Override
    public void onError(Call call, String errorMessage, ApiResponse apiResponse) {

    }



    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                char c = source.charAt(index);
                if (isCharAllowed(c))
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    private static boolean isCharAllowed(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
    }
}
