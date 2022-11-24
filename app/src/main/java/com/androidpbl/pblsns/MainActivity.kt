package com.androidpbl.pblsns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androidpbl.pblsns.event.EventManager
import com.androidpbl.pblsns.post.listeners.PostFindListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 커스텀 이벤트 리스너 등록
        EventManager.registerListener(PostFindListener())
    }

}