package com.androidpbl.pblsns.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var liveData = MutableLiveData<String>()
    var itemList = mutableListOf<String>()

    fun addList(item: String){
        itemList.add(item)
    }
}