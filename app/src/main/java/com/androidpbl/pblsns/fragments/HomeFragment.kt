package com.androidpbl.pblsns.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.activities.EnrollmentActivity
import com.androidpbl.pblsns.databinding.FragmentHomeBinding
import com.androidpbl.pblsns.databinding.PostShortLayoutBinding
import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.posts.Post
import com.androidpbl.pblsns.users.UserCache
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        if (posts.isEmpty()) {
            PostManager.collection.get().addOnSuccessListener {
                posts.addAll(it.toObjects(Post::class.java))

                if (posts.isNotEmpty()) {
                    // 상위 10개의 포스트만 추가
                    val maxIndex = 10.coerceAtMost(posts.size - 1)
                    for (i in 0..maxIndex) {
                        viewPosts.add(posts[i])
                    }

                    createCacheAndAttachAdapter(view)
                } else {
                    attachAdapter(view)
                }

            }.addOnFailureListener {
                Toast.makeText(activity, "Firestore 연결 오류", Toast.LENGTH_SHORT).show()
            }
        } else {


            // 상위 10개 포스트 추가
            val maxIndex = 10.coerceAtMost(posts.size - 1)
            for (i in 0..maxIndex) {
                viewPosts.add(posts[i])
            }

            createCacheAndAttachAdapter(view)
        }

        binding.postShort.addOnScrollListener(object: OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (scrollPercent(binding.postShort) >= 100) {
                    //Toast.makeText(activity, "새로운 게시물을 불러오는 중입니다", Toast.LENGTH_SHORT).show()

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

    private fun createCacheAndAttachAdapter(view: RecyclerView) {
        // 출력할 post 의 유저 정보 캐싱
        val uids = mutableListOf<String>()
        for (post in posts) {
            post.user?.let { uids.add(it) }
            Log.d("", "Find uid: ${post.user}")
        }

        val col = Firebase.firestore.collection("UserInfo")
        col.get().addOnSuccessListener {
            for (doc in it) {
                val uid = doc["uid"].toString()
                if (!UserCache.cache.containsKey(uid) && uids.contains(uid)) {
                    UserCache.cache[uid] = doc.toObject(EnrollmentActivity.UserInfo::class.java)
                }
            }

            // 유저 캐시가 끝난 후 어탭터 연결
            attachAdapter(view)
        }
    }

    private fun attachAdapter(view: RecyclerView) {
        view.adapter = SimplePostAdapter(viewPosts);
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
            val info = UserCache.cache[post.user]

            Log.d("", "user info: ${post.user}")

            val blackColor = Color.parseColor("#000000")

            //TODO: 유저 썸네일 바인딩
            binding.userThumb.setImageResource(R.drawable.ic_baseline_person_24)

            // 유저 이름, 포스트 바인딩
            binding.userName.text = info!!.nickname;
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

            //Log.d("test", "user = ${post.user}, post = ${post.post}")
        }

    }

}