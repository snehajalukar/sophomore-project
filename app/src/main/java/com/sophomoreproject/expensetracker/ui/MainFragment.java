package com.sophomoreproject.expensetracker.ui;

import android.content.Context;

import com.sophomoreproject.expensetracker.interfaces.IMainActivityListener;

public class MainFragment extends BaseFragment {

    protected IMainActivityListener mMainActivityListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivityListener = (IMainActivityListener)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivityListener = null;
    }

}
