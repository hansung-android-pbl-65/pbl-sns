package com.androidpbl.pblsns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FollowerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FollowerInfo> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follower);

        recyclerView=findViewById(R.id.recyclerView); //이름연결
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>(); //팔로우를 담은 리스트

        database=FirebaseDatabase.getInstance(); //파이어베이스 DB 연결
        databaseReference=database.getReference("kyw");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //파이어베이스 DB받아오는 곳
                arrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){ //데이터 list추출
                FollowerInfo user = snapshot.getValue(FollowerInfo.class);
                arrayList.add(user);
            }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { //에러처리

            }
        });

        adapter = new FollowerAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }
}