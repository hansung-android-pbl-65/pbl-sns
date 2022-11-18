package com.androidpbl.pblsns.post

import java.time.ZonedDateTime

abstract class AbstractPost {

    // 게시글 / 댓글 기본 정보
    var user: Int = 0
    var status: PostStatus = PostStatus.POST
    var post: String = ""

    // 게시글 / 댓글 상태 정보
    var isEdited: Boolean = false;
    lateinit var date: ZonedDateTime

    // 댓글 / 리플 정보
    lateinit var comments: List<AbstractPost>

}