package com.androidpbl.pblsns.post

import android.os.Build
import androidx.annotation.RequiresApi
import com.androidpbl.pblsns.post.events.PostFindEvent
import com.androidpbl.pblsns.post.posts.Post
import com.google.common.collect.Lists
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.logging.Level
import java.util.logging.Logger

object PostManager {

    private val logger by lazy { Logger.getLogger("Post") }
    private val db by lazy { Firebase.firestore }
    private val collection by lazy { db.collection("posts") }

    fun upload(post: Post) {
        collection.add(post)
            .addOnSuccessListener {
                logger.log(Level.INFO, "Upload success. id = ${it.id}")
            }
            .addOnFailureListener {
                logger.log(Level.WARNING, "Upload failed.", it)
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findById(postId: String) {
        collection.document(postId).get().addOnSuccessListener {
            if (it.exists()) {
                val post = it.toObject(Post::class.java)

                // call event
                PostFindEvent(Lists.newArrayList(post)).callEvent()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findByUser(user: Int) {
        collection.whereEqualTo("user", user).get().addOnSuccessListener {
            val posts: MutableList<Post> = Lists.newArrayList();
            for (doc in it) {
                val post = doc.toObject(Post::class.java)
                posts.add(post)
            }

            // call event
            PostFindEvent(posts).callEvent()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun findByTag(tag: String) {
        collection.whereArrayContains("tags", tag).get().addOnSuccessListener {
            val posts: MutableList<Post> = Lists.newArrayList();
            for (doc in it) {
                val post = doc.toObject(Post::class.java)
                posts.add(post)
            }

            // call event
            PostFindEvent(posts).callEvent()
        }
    }

}