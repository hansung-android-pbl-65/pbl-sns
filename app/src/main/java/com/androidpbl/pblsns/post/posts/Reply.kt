package com.androidpbl.pblsns.post.posts

import com.androidpbl.pblsns.post.PostStatus
import java.time.ZonedDateTime

class Reply constructor(_user: Int) {

    // 게시글 / 댓글 기본 정보
    private val user: Int = _user;
    private var status: PostStatus = PostStatus.POST
    private var reply: String = ""

    // 게시글 / 댓글 상태 정보
    private var isEdited: Boolean = false;
    private lateinit var date: ZonedDateTime

}