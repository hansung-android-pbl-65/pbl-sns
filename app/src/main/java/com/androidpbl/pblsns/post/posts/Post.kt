package com.androidpbl.pblsns.post.posts

import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.PostStatus
import java.time.ZonedDateTime

class Post constructor(_user: Int) {

    // 게시글 / 댓글 기본 정보
    private val user: Int = _user;
    private var status: PostStatus = PostStatus.POST
    private var post: String = ""

    // 게시글 / 댓글 상태 정보
    private var isEdited: Boolean = false;
    private lateinit var date: ZonedDateTime

    // 댓글 / 리플 정보
    private lateinit var comments: MutableList<Comment>

    fun upload() {
        PostManager.upload(this)
    }

    fun addComment(comment: Comment) {
        comments.add(comment);
    }

    fun getComment(index: Int) : Comment {
        return comments[index]
    }

}