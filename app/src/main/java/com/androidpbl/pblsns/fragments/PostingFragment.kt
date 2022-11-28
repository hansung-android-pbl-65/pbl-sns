package com.androidpbl.pblsns.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.activities.EnrollmentActivity
import com.androidpbl.pblsns.databinding.FragmentPostingBinding
import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.posts.Post
import com.androidpbl.pblsns.users.UserCache
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class PostingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPostingBinding.inflate(layoutInflater, container, false)

        binding.uploadButton.setOnClickListener {
            val content = binding.posting.text.toString()
            if (content.isNotEmpty()) {
                val uid = Firebase.auth.uid
                val post = Post(uid, content)

                if (uid != null) {
                    PostManager.collection.add(post)
                    binding.posting.text = null
                    Toast.makeText(context, "업로드 완료", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "유저 정보를 찾을 수 없습니다", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "업로드할 게시글을 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.resetButton.setOnClickListener {
            binding.posting.text = null
            Toast.makeText(context, "초기화 완료", Toast.LENGTH_SHORT).show()
        }

        binding.userThumbPosting.setImageResource(R.drawable.ic_baseline_person_24)

        val uid = Firebase.auth.uid
        if (UserCache.cache.containsKey(uid)) {
            binding.userNamePosting.text = UserCache.cache[uid]!!.nickname
        } else {
            Firebase.firestore.collection("UserInfo").whereEqualTo("uid", uid).get().addOnSuccessListener {
                UserCache.cache[uid!!] = it.toObjects(EnrollmentActivity.UserInfo::class.java)[0]
                binding.userNamePosting.text = UserCache.cache[uid]!!.nickname
            }
        }

        return binding.root
    }

    companion object {

        const val NAME = "게시글 작성"

    }
}