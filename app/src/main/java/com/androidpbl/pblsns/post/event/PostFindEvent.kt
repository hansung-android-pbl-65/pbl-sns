package com.androidpbl.pblsns.post.event

import com.androidpbl.pblsns.event.Event
import com.androidpbl.pblsns.post.posts.Post

class PostFindEvent constructor(val posts: MutableList<Post>) : Event() {



}