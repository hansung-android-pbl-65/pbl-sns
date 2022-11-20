package com.androidpbl.pblsns

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpbl.pblsns.databinding.FragmentSearchBinding
import com.androidpbl.pblsns.databinding.ItemRecyclerviewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchFragment : Fragment() {
    // 파이어베이스 구조 정해지면 수정
    //val db: FirebaseFirestore = Firebase.firestore
    //val rootCollectionReference = db.collection("/root")
    private val datas = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val searchView = binding.searchView


        var temp: String? = ""
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                temp = newText
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
//                파이어베이스 구조 정해지면 수정
//                rootCollectionReference.get().addOnSuccessListener {
//                    for(doc in it){
//                        if(doc.id.contains(temp!!)) {
////                            datas.clear()
//                            datas.add(doc.id)
//                        }
//                    }
//                }
                return true
            }
        })

        // 리사이클러뷰 테스트
        for(i in 1..9){
            datas.add("index $i")
        }

        val adapter = MyAdapter(datas)
        val layoutManager = LinearLayoutManager(activity)
//        binding.recyclerView.clearFocus()

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSearchBinding.bind(view)
    }


    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager){
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
    }





    class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun getItemCount(): Int {
            // 항목의 갯수 반환
            return datas.size
        }

        // single expression으로 viewHolder 객체를 반환
        // 자동으로 viewHolder 객체를 onBindViewHolder의 첫번째 인자로 대입
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(
            ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        // 첫번째 인자 : onCreateViewHolder에서 반환받은 ViewHolder 객체의 항목들
        // 두번째 인자 : getItemCount에서 반환받은 데이터 인덱스
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val binding = (holder as MyViewHolder).binding
            binding.itemData.text = datas[position]

            binding.itemRoot.setOnClickListener {
                // 태그 검색결과 -> 해당 short Post 목록 프래그먼트로 이동
                // 유저 검색결과 -> 해당 유저 프로필 프래그먼트로 이동
                Log.d("test", "clicked view ${position+1}")
            }
        }
    }
}