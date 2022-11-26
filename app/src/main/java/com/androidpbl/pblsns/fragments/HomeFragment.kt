package com.androidpbl.pblsns.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.databinding.FragmentHomeBinding
import com.androidpbl.pblsns.databinding.ItemRecyclerviewBinding
import com.androidpbl.pblsns.databinding.PostShortLayoutBinding
import com.androidpbl.pblsns.event.EventHandler
import com.androidpbl.pblsns.event.EventManager
import com.androidpbl.pblsns.event.Listener
import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.events.PostFindEvent
import com.androidpbl.pblsns.post.posts.Post
import com.google.common.collect.Lists
import kotlin.coroutines.coroutineContext

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val posts = mutableListOf<Post>();

        val view = binding.postShort
        view.clearFocus()
        view.setHasFixedSize(true);
        view.layoutManager = LinearLayoutManager(activity)

        PostManager.collection.get().addOnSuccessListener {
            posts.addAll(it.toObjects(Post::class.java))
            view.adapter = SimplePostAdapter(posts);
        }.addOnFailureListener {

            // 테스트 포스트 추가
            posts.add(Post("test_user_1", "포스트 테스트"))
            posts.add(Post("test_user_2", "포스트 테스트"))
            posts.add(Post("test_user_3", "포스트 테스트"))
            posts.add(Post("test_user_4", "포스트 테스트"))
            posts.add(Post("test_user_4", "포스트 테스트 1 \n포스트 테스트 2"))
            posts.add(Post("test_user_4", "포스트 테스트 1 \n포스트 테스트 2 \n포스트 테스트 3"))

            view.adapter = SimplePostAdapter(posts);
            Toast.makeText(activity, "Firestore 연결 오류", Toast.LENGTH_SHORT).show()
        }

        return binding.root;
    }

    companion object {

        const val NAME = "홈"

    }

    class SimplePostViewHolder(val binding: PostShortLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    class SimplePostAdapter constructor(var posts: MutableList<Post>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = PostShortLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SimplePostViewHolder(binding);
        }

        override fun getItemCount(): Int {
            return posts.size;
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as SimplePostViewHolder).binding
            val post = posts[position]

            //TODO: 유저 썸네일 바인딩
            binding.userThumb.setImageResource(R.drawable.ic_baseline_person_24)

            // 유저 이름, 포스트 바인딩
            binding.userName.text = post.user;
            binding.postShortContent.text = post.post;

            // 팔로우 이벤트 바인딩
            binding.followButton.setOnClickListener {
                //TODO: 클릭 상태에 따라 팔로우 추가/해제 기능 작동
                Log.d("test", "follow button click")
            }

            // 댓글 확인 바인딩
            binding.commentButton.setOnClickListener {
                //TODO: full 포스트 확인 페이지 이동
                Log.d("test", "comment button click")
            }

            // 좋아요 클릭 바인딩
            binding.likeButton.setOnClickListener {
                //TODO: 좋아요 추가/해제 기능 작동
                Log.d("test", "like button click")
            }

            Log.d("test", "user = ${post.user}, post = ${post.post}")
        }

    }

}