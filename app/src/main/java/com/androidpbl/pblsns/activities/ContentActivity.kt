package com.androidpbl.pblsns.activities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ContentActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private val db = Firebase.firestore
    private val myUid = FirebaseAuth.getInstance().uid
    // 추후 수정
    private val myPostsCollectionReference = db.collection("posts")
    private val channelID = "Post Modified"
    private var notifyID = 0

    private val frame: FrameLayout by lazy {
        findViewById(R.id.fragment_container)
    }

    private val bottomNavView: BottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        setSupportActionBar(findViewById(R.id.toolbar))

        // 초기 프래그먼트 설정
        changeFragment(HomeFragment(), HomeFragment.NAME);

        // 하단 네비게이션 클릭 시 화면 구성 프래그먼트 전환
        bottomNavView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    changeFragment(HomeFragment(), HomeFragment.NAME);
                    true;
                }
                R.id.nav_search -> {
                    changeFragment(SearchFragment(), SearchFragment.NAME)
                    true
                }
                R.id.nav_post -> {
                    changeFragment(PostingFragment(), PostingFragment.NAME)
                    true
                }
                R.id.nav_profile -> {
                    changeFragment(ProfileFragment(), ProfileFragment.NAME)
                    true
                }
                R.id.nav_note -> {
                    changeFragment(NotificationFragment(), NotificationFragment.NAME)
                    true
                }
                else -> {
                    Toast.makeText(applicationContext, "잘못된 버튼 입력입니다.", Toast.LENGTH_SHORT);
                    true
                }
            }
        }

        // notification
        // 게시물 변동사항 알림
        myPostsCollectionReference.addSnapshotListener{ snapshot, error ->
            for(doc in snapshot!!.documentChanges) {
                if((doc.document.data["user"] == myUid) && (doc.type == DocumentChange.Type.MODIFIED)){
                    showCommentNotification(channelID, doc.document.data["post"].toString())
                    viewModel.liveData.value = doc.document.data["post"].toString()
                    viewModel.addList("POST: ${viewModel.liveData.value.toString()}")
                }
            }
        }

        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
        createNotificationChannel(channelID)
    }



    private fun changeFragment(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().replace(frame.id, fragment).commit()
        supportActionBar?.title = name;
    }

    private fun showCommentNotification(channelID: String, myPost: String){
        val builder = NotificationCompat.Builder(this, channelID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("내 글에 변동사항이 있습니다")    // Notification Title
            .setContentText("POST: $myPost")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this)
            .notify(notifyID++, builder.build())
    }

    private fun createNotificationChannel(channelID: String){
        val channel = NotificationChannel(
            channelID, channelID,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = channelID
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun requestSinglePermission(permission: String){
        if(checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it == false){
                // permission is not granted
                AlertDialog.Builder(this).apply{
                    setTitle("Warning")
                    setMessage("Warning")
                }.show()
            }
        }

        if(shouldShowRequestPermissionRationale(permission)){
            // you should explain the reason why this app needs the permission
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Reason")
                setPositiveButton("Allow"){_, _ -> requestPermLauncher.launch(permission)}
                setNegativeButton("Deny"){_, _ -> }
            }.show()
        }else{
            // should be called in onCreated()
            requestPermLauncher.launch(permission)
        }
    }

}