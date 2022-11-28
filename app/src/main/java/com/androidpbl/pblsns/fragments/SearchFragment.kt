package com.androidpbl.pblsns.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.databinding.FragmentSearchBinding
import com.androidpbl.pblsns.databinding.ItemRecyclerviewBinding
import com.androidpbl.pblsns.post.posts.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val userInfoCollectionReference = db.collection("UserInfo")
    private val postsCollectionReference = db.collection("posts")
    private var userMap = hashMapOf<String, String>()
    private var postList = mutableListOf<Post>()
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                filterUserList(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
        })

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.clearFocus()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager

        // collection 에서 documents 불러오기
        userInfoCollectionReference.get().addOnSuccessListener { userDocs ->
            // 각 유저닉네임, uid 획득
            for(doc in userDocs){
                userMap[doc["nickname"].toString()] = doc["uid"].toString()
            }

            // 유저 게시글 불러오기
            postsCollectionReference.get().addOnSuccessListener { postDocs ->
                // 모든 게시글을 postList에 저장
                for(doc in postDocs) {
                    //val post = Post(doc["user"].toString(), doc["post"].toString())
                    val post = doc.toObject(Post::class.java)
                    //Log.d("post", "user: ${doc["user"].toString()} post: ${doc["post"].toString()}")
                    postList.add(post)
                }

                // userMap과 postList를 Adapter에 전달
                adapter = MyAdapter(userMap, postList, parentFragmentManager)
                binding.recyclerView.adapter = adapter

            }.addOnFailureListener {
                Toast.makeText(activity, "Get From FireStore Is Failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(activity, "Get From FireStore Is Failed", Toast.LENGTH_SHORT).show()
        }

        // 자동으로 검색창에 포커스 및 소프트키보드 올라오기
        val popUpSoftKeyboard =  View.OnFocusChangeListener { view, hasFocus ->
            val imm : InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if (hasFocus) {
                val isShowed = imm.showSoftInput(view.findFocus(), InputMethodManager.SHOW_IMPLICIT)
                Log.d("focus"," ## hasFocus -> $hasFocus , isShowing -> $isShowed")
            }
        }
        searchView.setOnQueryTextFocusChangeListener(popUpSoftKeyboard)

        return binding.root
    }// onCreateView

    fun filterUserList(text: String?){
        val filteredList = mutableListOf<String>()
        for((nickname, uid) in userMap){
            if(nickname.lowercase().contains(text!!.lowercase()))
                filteredList.add(nickname)
        }

        if (filteredList.isNotEmpty()) {
            adapter.setFilteredList(filteredList)
        }
    }// filterUserList

    class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    class MyAdapter(var userMap: HashMap<String, String>, var postList: MutableList<Post>, val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        private var userList: MutableList<String>

        init {
            userList = mutableListOf()
            for((nickname, uid) in userMap){
                userList.add(nickname)
            }
        }

        fun setFilteredList(filteredList: MutableList<String>){
            this.userList = filteredList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            // 항목의 갯수 반환
            return userList.size
        }

        // single expression으로 viewHolder 객체를 반환
        // 자동으로 viewHolder 객체를 onBindViewHolder의 첫번째 인자로 대입
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        // 첫번째 인자 : onCreateViewHolder에서 반환받은 ViewHolder 객체의 항목들
        // 두번째 인자 : getItemCount에서 반환받은 데이터 인덱스
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            // 뷰 텍스트에 데이터 대입
            binding.itemData.text = userList[position]

            binding.itemRoot.setOnClickListener {
                val selectedUser: String = userList[position]
                val selectedPosts: MutableList<Post> = mutableListOf()
                for(post in postList){  // post.user == uid
                    if(userMap[selectedUser] == post.user){
                        Log.d("uidCheck", "checked: ${userMap[selectedUser]} == ${post.user}")
                        selectedPosts.add(post)
                    }
                }


                if (selectedPosts.isNotEmpty()) { // 선택된 유저의 uid가 같은 post만 모아서 HomeFragment에 전달
//                    fragmentManager.commit{
//                        setReorderingAllowed(true)
//                        replace(R.id.fragment_container, HomeFragment(selectedPosts))
//                    }

                    fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment(selectedPosts)).commit()
                    Log.d("click", "nickname: ${selectedUser} uid: ${userMap[selectedUser]}")

                } else { // 유저의 게시글이 존재하지 않는다면 toast 전송
                    Toast.makeText(binding.root.context, "유저 게시글을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }// MyAdapter
    
    companion object {
        const val NAME = "검색"
    }
}