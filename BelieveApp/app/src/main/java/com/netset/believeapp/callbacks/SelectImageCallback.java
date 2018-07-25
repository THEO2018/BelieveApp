package com.netset.believeapp.callbacks;

import android.content.Intent;

/**
 * Created by netset on 30/1/18.
 */

public interface SelectImageCallback {
    void onSelectImage(int requestCode, int resultCode, Intent data);
}
