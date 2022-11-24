package com.androidpbl.pblsns.post.listeners

import com.androidpbl.pblsns.event.EventHandler
import com.androidpbl.pblsns.event.Listener
import com.androidpbl.pblsns.post.events.PostFindEvent

class PostFindListener : Listener {

    @EventHandler
    fun onPostFind(event: PostFindEvent) {
        val posts = event.posts;



    }

}