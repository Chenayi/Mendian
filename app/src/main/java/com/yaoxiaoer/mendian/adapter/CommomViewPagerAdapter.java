package com.yaoxiaoer.mendian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chenwy on 2018/1/29.
 */

public class CommomViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public CommomViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments == null || mFragments.size() <= 0 ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    public List<Fragment> getFragments() {
        return mFragments == null ? new ArrayList<Fragment>() : mFragments;
    }
}
