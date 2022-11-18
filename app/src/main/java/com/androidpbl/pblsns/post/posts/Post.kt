package com.androidpbl.pblsns.post.posts

import com.androidpbl.pblsns.post.PostManager

class Post constructor(user: Int) : AbstractPost(user) {

    fun upload() {
        PostManager.upload(this)
    }

    fun addComment(comment: Comment) {
        comments.add(comment);
    }

    fun getComment(index: Int) : Comment {
        return comments[index] as Comment
    }

}