package com.androidpbl.pblsns.post.posts

import com.androidpbl.pblsns.post.PostEditor
import com.androidpbl.pblsns.post.PostStatus
import org.w3c.dom.Comment
import java.time.ZonedDateTime

abstract class AbstractPost constructor(user: Int) {

    // 게시글 / 댓글 기본 정보
    protected val user: Int = user;
    protected var status: PostStatus = PostStatus.POST
    protected var post: String = ""

    // 게시글 / 댓글 상태 정보
    protected var isEdited: Boolean = false;
    protected lateinit var date: ZonedDateTime

    // 댓글 / 리플 정보
    protected lateinit var comments: MutableList<AbstractPost>

    fun getEditor(): PostEditor {
        return PostEditor(this);
    }

}