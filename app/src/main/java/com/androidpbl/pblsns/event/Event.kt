package com.androidpbl.pblsns.event

import android.os.Build
import androidx.annotation.RequiresApi

abstract class Event {

    @RequiresApi(Build.VERSION_CODES.O)
    fun callEvent() {
        EventManager.callEvent(this);
    }

}