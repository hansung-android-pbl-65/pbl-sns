package com.androidpbl.pblsns.activities

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContentActivity : AppCompatActivity() {

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
    }

    private fun changeFragment(fragment: Fragment, name: String) {
        supportFragmentManager.beginTransaction().replace(frame.id, fragment).commit()
        supportActionBar?.title = name;
    }

}