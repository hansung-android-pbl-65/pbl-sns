package com.androidpbl.pblsns.post.posts

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.PostStatus
import com.google.common.collect.Lists
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class Post constructor(_user: String?, _post: String) {

    constructor() : this("", "")

    // 게시글 / 댓글 기본 정보
    val user: String? = _user;
    var status: PostStatus = PostStatus.POST
    var post: String = _post

    // 게시글 / 댓글 상태 정보
    var isEdited: Boolean = false;

    @RequiresApi(Build.VERSION_CODES.O)
    var date = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

    // 댓글 / 리플 정보
    var comments: MutableList<Comment> = Lists.newArrayList()


}