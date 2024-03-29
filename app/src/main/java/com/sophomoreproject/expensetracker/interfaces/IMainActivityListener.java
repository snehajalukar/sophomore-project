package com.sophomoreproject.expensetracker.interfaces;

import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.View;

import com.sophomoreproject.expensetracker.ui.MainActivity;

import java.util.List;

public interface IMainActivityListener {

    void setMode(@MainActivity.NavigationMode int mode);
    void setTabs(List<String> tabList, TabLayout.OnTabSelectedListener onTabSelectedListener);
    void setFAB(@DrawableRes int drawableId, View.OnClickListener onClickListener);
    void setTitle(String title);
    void setPager(ViewPager vp, TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener);
    void setExpensesSummary(@IDateMode int dateMode);
    ActionMode setActionMode(ActionMode.Callback actionModeCallback);

}
