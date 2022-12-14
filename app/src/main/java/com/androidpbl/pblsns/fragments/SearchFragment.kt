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

        // collection ?????? documents ????????????
        userInfoCollectionReference.get().addOnSuccessListener { userDocs ->
            // ??? ???????????????, uid ??????
            for(doc in userDocs){
                userMap[doc["nickname"].toString()] = doc["uid"].toString()
            }

            // ?????? ????????? ????????????
            postsCollectionReference.get().addOnSuccessListener { postDocs ->
                // ?????? ???????????? postList??? ??????
                for(doc in postDocs) {
                    //val post = Post(doc["user"].toString(), doc["post"].toString())
                    val post = doc.toObject(Post::class.java)
                    //Log.d("post", "user: ${doc["user"].toString()} post: ${doc["post"].toString()}")
                    postList.add(post)
                }

                // userMap??? postList??? Adapter??? ??????
                adapter = MyAdapter(userMap, postList, parentFragmentManager)
                binding.recyclerView.adapter = adapter

            }.addOnFailureListener {
                Toast.makeText(activity, "Get From FireStore Is Failed", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(activity, "Get From FireStore Is Failed", Toast.LENGTH_SHORT).show()
        }

        // ???????????? ???????????? ????????? ??? ?????????????????? ????????????
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
            // ????????? ?????? ??????
            return userList.size
        }

        // single expression?????? viewHolder ????????? ??????
        // ???????????? viewHolder ????????? onBindViewHolder??? ????????? ????????? ??????
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        // ????????? ?????? : onCreateViewHolder?????? ???????????? ViewHolder ????????? ?????????
        // ????????? ?????? : getItemCount?????? ???????????? ????????? ?????????
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            // ??? ???????????? ????????? ??????
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


                if (selectedPosts.isNotEmpty()) { // ????????? ????????? uid??? ?????? post??? ????????? HomeFragment??? ??????
//                    fragmentManager.commit{
//                        setReorderingAllowed(true)
//                        replace(R.id.fragment_container, HomeFragment(selectedPosts))
//                    }

                    fragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment(selectedPosts)).commit()
                    Log.d("click", "nickname: ${selectedUser} uid: ${userMap[selectedUser]}")

                } else { // ????????? ???????????? ???????????? ???????????? toast ??????
                    Toast.makeText(binding.root.context, "?????? ???????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }// MyAdapter
    
    companion object {
        const val NAME = "??????"
    }
}