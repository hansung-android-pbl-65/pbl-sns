package com.androidpbl.pblsns.post.posts

open class Comment constructor(user: Int) : AbstractPost(user) {

    fun addReply(comment: Reply) {
        comments.add(comment);
    }

    fun getReply(index: Int) : Reply {
        return comments[index] as Reply
    }

    fun getReplies() : MutableList<AbstractPost> {
        return comments;
    }

}