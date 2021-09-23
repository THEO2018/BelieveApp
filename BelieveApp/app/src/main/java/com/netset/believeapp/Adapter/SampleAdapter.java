package com.netset.believeapp.Adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.netset.believeapp.Fragment.EditorFragment;

/**
 * Created by netset on 17/1/18.
 */

public class SampleAdapter extends FragmentPagerAdapter {
    private final Context ctxt;
    private int pageCount=3;

    public SampleAdapter(Context ctxt, FragmentManager mgr) {
        super(mgr);

        this.ctxt=ctxt;
    }

    @Override
    public int getCount() {
        return(pageCount);
    }

    @Override
    public Fragment getItem(int position) {
        return(EditorFragment.newInstance(position));
    }

    @Override
    public String getPageTitle(int position) {
        return(EditorFragment.getTitle(ctxt, position));
    }

    void setPageCount(int pageCount) {
        this.pageCount=pageCount;
    }
}
