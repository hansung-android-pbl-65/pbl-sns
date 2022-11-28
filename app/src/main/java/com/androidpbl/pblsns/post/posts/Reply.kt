package com.androidpbl.pblsns.post.posts

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidpbl.pblsns.post.PostStatus
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Reply constructor(_user: String) {

    constructor() : this("")

    // 게시글 / 댓글 기본 정보
    val user: String = _user;
    var status: PostStatus = PostStatus.POST
    var reply: String = ""

    // 게시글 / 댓글 상태 정보
    var isEdited: Boolean = false;

    @RequiresApi(Build.VERSION_CODES.O)
    var date: String = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);

}