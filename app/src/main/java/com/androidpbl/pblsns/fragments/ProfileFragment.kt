package com.androidpbl.pblsns.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.androidpbl.pblsns.activities.LoginActivity
import com.androidpbl.pblsns.databinding.FragmentProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)


        binding!!.logoutBtn.setOnClickListener {
            //firebase auth에서 sign out 기능 호출
            Firebase.auth.signOut()
            var intent= Intent(activity, LoginActivity::class.java) //로그인 페이지 이동\
            startActivity(intent)
            activity?.finish()
        }

        return binding!!.root
    }

    companion object {

        const val NAME = "프로필"



    }
}