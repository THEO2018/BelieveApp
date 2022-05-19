package com.netset.believeapp;

import android.content.Context;
import android.media.MediaMetadataRetriever;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.Collections;
import java.util.Set;

public class GalaryFilter extends Filter {

    @Override
    protected Set<MimeType> constraintTypes() {
        return Collections.singleton(MimeType.MP4);
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        if (!needFiltering(context, item))
            return null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(context, item.uri);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
     /*   if (time != null) {
            long timeInMillis = Long.parseLong(time);
            if (timeInMillis > 41* 1000) {
                return new IncapableCause(IncapableCause.DIALOG, "You can select maximum 40 sec video.");
            }
        }*/
        return null;
    }
}