package com.netset.believeapp.music;

import android.widget.SeekBar;

/**
 * Created by netset on 24/1/18.
 */

public interface OnPlayActionClick {
    void onPlayTrack();
    void onPauseTrack(SeekBar seekbar);
    void onClickNextTrack();
    void onClickPreviousTrack();
    void onSeekbarUpdate(SeekBar seekbar);
}
