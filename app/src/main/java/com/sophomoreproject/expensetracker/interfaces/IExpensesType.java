package com.sophomoreproject.expensetracker.interfaces;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({IExpensesType.MODE_EXPENSES, IExpensesType.MODE_INCOME})
@Retention(RetentionPolicy.SOURCE)
public @interface IExpensesType{
    int MODE_EXPENSES = 1;
    int MODE_INCOME = 0;
}
