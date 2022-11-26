package com.androidpbl.pblsns

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidpbl.pblsns.fragments.HomeFragment
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
                    //TODO: 검색 프래그먼트로 전환
                    true
                }
                R.id.nav_post -> {
                    //TODO: 포스팅 프래그먼트로 전환
                    true
                }
                R.id.nav_profile -> {
                    //TODO: 프로필 프래그먼트로 전환
                    true
                }
                R.id.nav_note -> {
                    //TODO: 알림 프래그먼트로 전환
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