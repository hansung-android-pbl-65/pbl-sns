package com.androidpbl.pblsns.activities

import androidx.appcompat.app.AppCompatActivity

enum class ActivityType(val clazz: Class<out AppCompatActivity>) {

    HOME(HomeActivity::class.java),
    POST(PostActivity::class.java),
    PROFILE(ProfileActivity::class.java);

}