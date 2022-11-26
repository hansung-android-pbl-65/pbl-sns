package com.androidpbl.pblsns.fragments

import android.content.Context
import android.graphics.Color
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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
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

class HomeFragment constructor(private var posts: MutableList<Post>) : Fragment() {

    private val viewPosts = mutableListOf<Post>()

    constructor() : this(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val view = binding.postShort
        view.clearFocus()
        view.setHasFixedSize(true);
        view.layoutManager = LinearLayoutManager(activity)

        // parameter 로 넘겨받은 사이즈가 1보다 작으면 포스트 조회
        if (posts.size < 1) {
            PostManager.collection.get().addOnSuccessListener {
                posts.addAll(it.toObjects(Post::class.java))

                if (posts.isNotEmpty()) {
                    // 상위 10개의 포스트만 추가
                    val maxIndex = 10.coerceAtMost(posts.size)
                    for (i in 0..maxIndex) {
                        viewPosts.add(posts[i])
                    }
                }

                view.adapter = SimplePostAdapter(viewPosts);
            }.addOnFailureListener {

                val tag = "포스트 테스트"
                var msg = ""

                // 테스트 포스트 추가
                repeat(30) {
                    msg = "$msg\n$tag $it"
                    viewPosts.add(Post("test_user_$it", msg))
                }

                view.adapter = SimplePostAdapter(viewPosts);
                Toast.makeText(activity, "Firestore 연결 오류", Toast.LENGTH_SHORT).show()
            }
        }

        binding.postShort.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (scrollPercent(binding.postShort) >= 100) {
                    Toast.makeText(activity, "새로운 게시물을 불러오는 중입니다", Toast.LENGTH_SHORT).show()

                    val startIndex = viewPosts.size
                    val endIndex = (startIndex + 10).coerceAtMost(posts.size - 1)
                    for (i in startIndex..endIndex) {
                        viewPosts.add(posts[i])
                    }

                    view.adapter?.notifyItemRangeInserted(startIndex, endIndex - startIndex)
                }
            }
        })

        return binding.root;
    }

    fun scrollPercent(view: RecyclerView): Double {
        return (view.computeVerticalScrollOffset() * 1.0 / (view.computeVerticalScrollRange() - view.computeVerticalScrollExtent())) * 100.0
    }

    companion object {

        const val NAME = "홈"

    }

    class SimplePostViewHolder(val binding: PostShortLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    class SimplePostAdapter constructor(private var posts: MutableList<Post>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            val blackColor = Color.parseColor("#000000")

            //TODO: 유저 썸네일 바인딩
            binding.userThumb.setImageResource(R.drawable.ic_baseline_person_24)

            // 유저 이름, 포스트 바인딩
            binding.userName.text = post.user;
            binding.userName.setTextColor(blackColor)

            binding.postShortContent.text = post.post;
            binding.postShortContent.setTextColor(blackColor)

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