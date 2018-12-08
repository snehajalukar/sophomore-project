package com.sophomoreproject.expensetracker.interfaces;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

public interface IFragmentListener {

    void replaceFragment(Fragment fragment, boolean addToBackStack);
    void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack);
    void setResultWithData(int status, Intent intent);
    void setToolbar(Toolbar toolbar);
    void closeActivity();

}
