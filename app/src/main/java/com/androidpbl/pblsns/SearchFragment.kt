package com.androidpbl.pblsns

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpbl.pblsns.databinding.FragmentSearchBinding
import com.androidpbl.pblsns.databinding.ItemRecyclerviewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    // 파이어베이스 구조 정해지면 수정
    private val db: FirebaseFirestore = Firebase.firestore
    private val rootCollectionReference = db.collection("/root")
    private var itemList = mutableListOf<String>()
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
                if(newText!!.indexOf("#") == 0)
                    filterTagList(newText)
                else
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
        rootCollectionReference.get().addOnSuccessListener {
            // 각 document id를 add
            for(doc in it){
                itemList.add(doc.id)
            }
            // document id를 담은 itemList를 Adapter에 전달
            adapter = MyAdapter(itemList)
            binding.recyclerView.adapter = adapter
        }.addOnFailureListener {
            Toast.makeText(activity, "Get From FireStore Is Failed", Toast.LENGTH_SHORT).show()
        }

        // 자동으로 검색창에 포커스 및 소프트키보드 올라오기
        val popUpSoftKeyboard =  View.OnFocusChangeListener { view, hasFocus ->
            val imm : InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            if (hasFocus) {
                val isShowed = imm.showSoftInput(view.findFocus(), InputMethodManager.SHOW_IMPLICIT)
                Log.d("focus"," ## hasFocus -> $hasFocus , isShowing -> $isShowed")        // both are true!
            }
        }
        searchView.setOnQueryTextFocusChangeListener(popUpSoftKeyboard)

        return binding.root
    }// onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
    }// onViewCreated

    fun filterUserList(text: String?){
        val filteredList = mutableListOf<String>()
        for(item in itemList){
            if(item.lowercase().contains(text!!.lowercase())){
                // 입력받은 text를 item이 포함한다면
                filteredList.add(item)
            }
        }

        if(filteredList.isEmpty()){
            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show()
        }else{
            adapter.setFilteredList(filteredList)
        }
    }// filteList

    fun filterTagList(text: String?){
        val filteredTagList = mutableListOf<String>()
        for(item in itemList){
            if(item.indexOf("#") == 0){ // 첫번째 캐릭터가 #이면
                if(item.lowercase().contains(text!!.lowercase()))
                    filteredTagList.add(item)
            }
        }

        if(filteredTagList.isEmpty()){
            Toast.makeText(activity, "No Data Found", Toast.LENGTH_SHORT).show()
        }else{
            adapter.setFilteredList(filteredTagList)
        }
    }

    class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    class MyAdapter(var itemList: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        fun setFilteredList(filteredList: MutableList<String>){
            this.itemList = filteredList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            // 항목의 갯수 반환
            return itemList.size
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
            binding.itemData.text = itemList[position]

            binding.itemRoot.setOnClickListener {
                // 태그 검색결과 -> 해당 short Post 목록 프래그먼트로 이동
                // 유저 검색결과 -> 해당 유저 프로필 프래그먼트로 이동
                Log.d("test", "clicked view ${position+1}")
            }
        }
    }// MyAdapter
}