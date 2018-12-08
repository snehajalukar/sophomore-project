package com.sophomoreproject.expensetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sophomoreproject.expensetracker.entities.Reminder;

public class AlarmsSystemBooted extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            for (Reminder reminder : Reminder.getReminders()) {
                if (reminder.isState()) {
                    Reminder.updateReminder(reminder, reminder.isState());
                }
            }
        }
    }

}
