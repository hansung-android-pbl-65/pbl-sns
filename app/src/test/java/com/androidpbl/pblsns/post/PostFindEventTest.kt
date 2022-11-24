package com.androidpbl.pblsns.post

import com.androidpbl.pblsns.event.EventHandler
import com.androidpbl.pblsns.event.EventManager
import com.androidpbl.pblsns.event.Listener
import com.androidpbl.pblsns.post.events.PostFindEvent
import com.androidpbl.pblsns.post.posts.Post
import com.google.common.collect.Lists
import org.junit.Test
import java.util.logging.Level
import java.util.logging.Logger

class PostFindEventTest {

    companion object {
        private val logger = Logger.getLogger("TEST");
    }

    @Test
    fun callEvent() {

        logger.log(Level.INFO, "Register listener.");
        EventManager.registerListener(PostFindEventListener());

        logger.log(Level.INFO, "Call event.")
        val posts : MutableList<Post> = Lists.newArrayList(Post(1), Post(2), Post(3));
        PostFindEvent(posts).callEvent();
    }

    class PostFindEventListener : Listener {

        @EventHandler
        fun onPostFind(event : PostFindEvent) {
            val posts = event.posts;
            logger.log(Level.INFO, "Event listened. posts size = ${posts.size}")
        }

    }

}