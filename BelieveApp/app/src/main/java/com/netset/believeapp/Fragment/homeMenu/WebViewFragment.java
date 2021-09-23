package com.netset.believeapp.Fragment.homeMenu;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.netset.believeapp.Fragment.BaseFragment;
import com.netset.believeapp.R;
import com.netset.believeapp.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by netset on 27/4/18.
 */

public class WebViewFragment extends BaseFragment {

    @BindView(R.id.webView)
    WebView webView;
    Unbinder unbinder;

    String title, webUrl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.webview_fragment, null);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if(b!= null){
            title = b.getString("newsname");
            webUrl = b.getString("newslink");
            ((HomeActivity) getActivity()).setToolbarTitle(title, true, false, false, null);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(webUrl);

        }

        //  setBlogAdapter();
    }



}
