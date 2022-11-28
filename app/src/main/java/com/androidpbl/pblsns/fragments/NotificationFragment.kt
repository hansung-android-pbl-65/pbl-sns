package com.androidpbl.pblsns.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpbl.pblsns.R
import com.androidpbl.pblsns.activities.MyViewModel
import com.androidpbl.pblsns.databinding.FragmentNotificationBinding
import com.androidpbl.pblsns.databinding.NotificationRecyclerviewBinding


class NotificationFragment : Fragment() {

    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var adapter: MyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentNotificationBinding.inflate(layoutInflater, container, false)

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        adapter = MyAdapter(viewModel.itemList)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner){
            adapter.refreshList(viewModel.itemList)
        }
    }

    class MyViewHolder(val binding: NotificationRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

    class MyAdapter(var itemList: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        fun refreshList(itemList: MutableList<String>){
            this.itemList = itemList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return itemList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = MyViewHolder(NotificationRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
            val binding = (holder as MyViewHolder).binding
            binding.notificationTitle.text = "새로운 알림 ${itemList.size - position}"
            binding.notificationData.text = itemList[itemList.size - position - 1]

            binding.itemRoot.setOnClickListener {
                Log.d("test", "clicked")
            }
        }
    }

    companion object {
        const val NAME = "알림"
    }
}