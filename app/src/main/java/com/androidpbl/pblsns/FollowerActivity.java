package com.androidpbl.pblsns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

//firestore db
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/*firestore db 아닌 realtime db 여서 삭제
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/

import java.util.ArrayList;

public class FollowerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<FollowerInfo> arrayList;
    private FirebaseFirestore database;
    private DocumentReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.follower);

        recyclerView=findViewById(R.id.recyclerView); //이름연결
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>(); //팔로우를 담은 리스트

        database=FirebaseFirestore.getInstance(); //파이어스토어 DB 연결
        databaseReference=database.collection("kyw").document(); //컬렉션에서 문서가져옴
        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot dataSnapshot) {
                //파이어베이스 DB받아오는 곳
                arrayList.clear();

               for(DocumentSnapshot snapshot:dataSnapshot.get()){ //데이터 list추출해야하는데 오류
                    FollowerInfo user = snapshot.toObject(FollowerInfo.class);
                    arrayList.add(user);
            }
                adapter.notifyDataSetChanged();
            }

            /*@Override
            public void onCancelled(@NonNull DatabaseError error) { //에러처리
            }
            */
        });

        adapter = new FollowerAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
    }
}