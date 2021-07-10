package com.jpdevzone.knowyourdreams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpdevzone.knowyourdreams.adapters.AlphabetAdapter
import com.jpdevzone.knowyourdreams.adapters.RecyclerViewAdapter
import com.jpdevzone.knowyourdreams.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val searchView = binding.searchView
        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        val mRecyclerView1: RecyclerView = findViewById(R.id.alphabet)
        mRecyclerView1.setHasFixedSize(true)
        val mLayoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        val mAdapter1: RecyclerView.Adapter<*> = AlphabetAdapter(alphabet())

        mRecyclerView1.layoutManager = mLayoutManager1
        mRecyclerView1.adapter = mAdapter1

        val dreams = Constants.getDreams()

        val mRecyclerView2: RecyclerView = findViewById(R.id.dashboardRecyclerView)
        mRecyclerView2.setHasFixedSize(true)
        val mLayoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        val mAdapter2: RecyclerView.Adapter<*> = RecyclerViewAdapter(dreams)

        mRecyclerView2.layoutManager = mLayoutManager2
        mRecyclerView2.adapter = mAdapter2

    }
    private fun alphabet(): ArrayList<String> {
        val list = ArrayList<String>()
        val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЮЯ".toCharArray()

        for (i in alphabet) {
            list.add("$i")
        }
        return list
    }
}