package com.androidpbl.pblsns.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//firestore db
import com.androidpbl.pblsns.FollowerAdapter;
import com.androidpbl.pblsns.FollowerInfo;
import com.androidpbl.pblsns.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FollowerFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FollowerInfo> arrayList;
    private FirebaseFirestore database;
    private DocumentReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.follower, container, false);
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.follower);

        recyclerView=view.findViewById(R.id.recyclerView); //이름연결
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>(); //팔로우를 담은 리스트

        database=FirebaseFirestore.getInstance(); //파이어스토어 DB 연결
        //databaseReference=database.collection("kyw").get(); //컬렉션에서 문서가져옴
        database.collection("kyw").get().addOnSuccessListener(runnable -> {
        for (QueryDocumentSnapshot queryDocumentSnapshot : runnable) {
        FollowerInfo user = queryDocumentSnapshot.toObject(FollowerInfo.class);
        arrayList.add(user);
    }
        adapter.notifyDataSetChanged();
    });

        adapter = new FollowerAdapter(arrayList,  getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }


}