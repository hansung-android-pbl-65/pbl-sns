package com.androidpbl.pblsns.post.posts

import com.androidpbl.pblsns.post.PostStatus
import java.time.ZonedDateTime

open class Comment constructor(_user: Int) {

    // 게시글 / 댓글 기본 정보
    private val user: Int = _user;
    private var status: PostStatus = PostStatus.POST
    private var comment: String = ""

    // 게시글 / 댓글 상태 정보
    private var isEdited: Boolean = false;
    private lateinit var date: ZonedDateTime

    // 댓글 / 리플 정보
    private lateinit var replies: MutableList<Reply>

    fun addReply(comment: Reply) {
        replies.add(comment);
    }

    fun getReply(index: Int) : Reply {
        return replies[index] as Reply
    }

}