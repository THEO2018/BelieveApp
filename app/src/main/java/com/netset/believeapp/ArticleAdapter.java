/*
 *   Copyright 2016 Marco Gomiero
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.netset.believeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.kedia.ogparser.OpenGraphCallback;
import com.kedia.ogparser.OpenGraphParser;
import com.kedia.ogparser.OpenGraphResult;
import com.netset.believeapp.Utils.CommonDialogs;
import com.prof.rssparser.Article;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Marco Gomiero on 12/02/2015.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private ArrayList<Article> articles;

    private int rowLayout;
    private Context mContext;
    private WebView articleView;

    public ArticleAdapter(ArrayList<Article> list, int rowLayout, Context context) {
        this.articles = list;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    public void clearData() {
        if (articles != null)
            articles.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        Article currentArticle = articles.get(position);

        Log.d("currentArticle", "onBindViewHolder: " + currentArticle);

        Locale.setDefault(Locale.getDefault());


        if(currentArticle.getPubDate()!=null && !currentArticle.getPubDate().equals("")){
            Date date = currentArticle.getPubDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            final String pubDateString = sdf.format(date);
            viewHolder.pubDate.setText(pubDateString);
        }else{
            viewHolder.pubDate.setText("");
        }

        viewHolder.title.setText(currentArticle.getTitle());

        /*Picasso.get()
                .load(currentArticle.getImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.image);*/

        Log.e("image>>>>>>>>>>>>",">>>>>>>"+currentArticle.getImage());
//        CommonDialogs.getSquareImage3(mContext,currentArticle.getImage(),viewHolder.image);
        OpenGraphParser openGraphParser =new OpenGraphParser(new OpenGraphCallback() {
            @Override
            public void onPostResponse(@NotNull OpenGraphResult openGraphResult) {
                if (openGraphResult.getImage()!=null){
                    CommonConst.Companion.loadGlide(mContext.getApplicationContext(), openGraphResult.getImage(),R.drawable.empty).into(viewHolder.image);


                }
            }

            @Override
            public void onError(@NotNull String s) {

            }
        }, true, mContext.getApplicationContext());
        openGraphParser.parse(currentArticle.getLink()); // To parse the link provided

      /*  if (currentArticle.getImage()!=null){
            CommonConst.Companion.loadGlide(mContext,currentArticle.getImage(),null).into(viewHolder.image);
        }*/
        if(currentArticle.getDescription()!=null && !currentArticle.getDescription().equals("")){
            viewHolder.category.setVisibility(View.VISIBLE);
            viewHolder.category.setText(currentArticle.getDescription());

        }else{
            viewHolder.category.setVisibility(View.GONE);
        }


       /* String categories = "";
        if (currentArticle.getCategories() != null) {
            for (int i = 0; i < currentArticle.getCategories().size(); i++) {
                if (i == currentArticle.getCategories().size() - 1) {
                    categories = categories + currentArticle.getCategories().get(i);
                } else {
                    categories = categories + currentArticle.getCategories().get(i) + ", ";
                }
            }
        }*/

    /*    if(categories.equals("")){
            viewHolder.category.setVisibility(View.GONE);
        }else{
            viewHolder.category.setVisibility(View.VISIBLE);
        }
        viewHolder.category.setText(categories);*/

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              /*  String title = articles.get(viewHolder.getAdapterPosition()).getTitle();
                Bundle args = new Bundle();
                args.putString("newslink",articles.get(viewHolder.getAdapterPosition()).getLink() + "");
                args.putString("newsname",title);
                ((BaseActivity)mContext).navigateFragmentTransaction_ARG(R.id.homeContainer, new WebViewFragment(),args);
*/
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext,R.style.Theme_AppCompat_DialogWhenLarge);
                String title = articles.get(viewHolder.getAdapterPosition()).getTitle();
                alert.setTitle(title);

                WebView wv = new WebView(mContext);
                wv.loadUrl(articles.get(viewHolder.getAdapterPosition()).getLink() + "");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();
              /*  //show article content inside a dialog
                articleView = new WebView(mContext);

               // articleView.getSettings().setLoadWithOverviewMode(true);

                String title = articles.get(viewHolder.getAdapterPosition()).getTitle();
                String content = articles.get(viewHolder.getAdapterPosition()).getContent();

                articleView.getSettings().setJavaScriptEnabled(true);
                articleView.setHorizontalScrollBarEnabled(false);
                articleView.setWebChromeClient(new WebChromeClient());
                articleView.loadUrl(articles.get(viewHolder.getAdapterPosition()).getLink() + "");



            *//*    articleView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " +

                        "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + content, null, "utf-8", null);
               *//*
                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(mContext,R.style.Theme_AppCompat_DialogWhenLarge).create();
                alertDialog.setTitle(title);
                alertDialog.setView(articleView);
                alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                ((TextView) alertDialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());*/
            }
        });
    }

    @Override
    public int getItemCount() {

        return articles == null ? 0 : articles.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        ImageView image;
        TextView category;

        public ViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            pubDate = itemView.findViewById(R.id.pubDate);
            image = itemView.findViewById(R.id.image);
           category = itemView.findViewById(R.id.categories);
        }
    }
}