package com.androidpbl.pblsns

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class FollowerActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var arrayList: ArrayList<FollowerInfo> = arrayListOf()
    private var database: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.follower_activity)
        recyclerView = findViewById(R.id.recyclerView) //이름연결
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.setLayoutManager(layoutManager)
        arrayList = ArrayList() //팔로우를 담은 리스트
        database = FirebaseDatabase.getInstance() //파이어베이스 DB 연결
        databaseReference = database!!.getReference("kyw")
        databaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //파이어베이스 DB받아오는 곳
                arrayList!!.clear()
                for (snapshot in dataSnapshot.children) { //데이터 list추출
                    val user = snapshot.getValue(FollowerInfo::class.java)
                    arrayList!!.add(user!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        adapter = FollowerAdapter(arrayList, this)
        recyclerView!!.setAdapter(adapter)
    }
}