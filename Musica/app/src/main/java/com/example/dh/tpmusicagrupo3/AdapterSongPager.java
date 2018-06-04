package com.example.dh.tpmusicagrupo3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by DH on 4/6/2018.
 */

public class AdapterSongPager extends FragmentStatePagerAdapter {
    private List<SongFragment> fragments;
    public AdapterSongPager(FragmentManager fm, List<SongFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
