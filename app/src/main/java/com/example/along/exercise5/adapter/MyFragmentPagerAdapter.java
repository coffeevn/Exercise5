package com.example.along.exercise5.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.along.exercise5.R;
import com.example.along.exercise5.fragment.DoneFragment;
import com.example.along.exercise5.fragment.ToDoFragment;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    public MyFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ToDoFragment();
        } else {
            return new DoneFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.todo);
            case 1:
                return mContext.getString(R.string.done);
            default:
                return null;
        }
    }
}
