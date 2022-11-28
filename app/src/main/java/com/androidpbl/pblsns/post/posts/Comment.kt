package com.androidpbl.pblsns.post.posts

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidpbl.pblsns.post.PostStatus
import com.google.common.collect.Lists
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class Comment constructor(_user: String) {

    constructor() : this("")

    // 게시글 / 댓글 기본 정보
    val user: String = _user;
    var status: PostStatus = PostStatus.POST
    var comment: String = ""

    // 게시글 / 댓글 상태 정보
    var isEdited: Boolean = false;

    @RequiresApi(Build.VERSION_CODES.O)
    var date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

    // 댓글 / 리플 정보
    var replies: MutableList<Reply> = Lists.newArrayList()

    fun addReply(comment: Reply) {
        replies.add(comment);
    }

    fun getReply(index: Int) : Reply {
        return replies[index]
    }

}