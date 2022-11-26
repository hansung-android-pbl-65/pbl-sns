package com.androidpbl.pblsns.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.event.EventHandler
import com.androidpbl.pblsns.event.EventManager
import com.androidpbl.pblsns.event.Listener
import com.androidpbl.pblsns.post.PostManager
import com.androidpbl.pblsns.post.events.PostFindEvent

class HomeFragment : Fragment(), Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventManager.registerListener(this);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        PostManager.findLatestPost(10)
        return view;
    }

    @EventHandler
    fun onPostFind(event: PostFindEvent) {
        val posts = event.posts;

        val adapter = view?.findViewById<ListView>(R.id.listview_post_short)

    }

    companion object {

        const val NAME = "홈"

        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }

    }
}