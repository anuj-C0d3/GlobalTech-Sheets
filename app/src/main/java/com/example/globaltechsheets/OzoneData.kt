package com.example.globaltechsheets


import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.globaltechsheets.databinding.ActivityOzoneDataBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OzoneData : AppCompatActivity() {
    private lateinit var binding:ActivityOzoneDataBinding
    private lateinit var datalist:ArrayList<Jobsheetdata>
    private lateinit var adapter:Adapterclass
    private lateinit var initlist :List<Jobsheetdata>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbobject = database.getDatabase(this)
        val db = dbobject.ozonDao()
        datalist = java.util.ArrayList()
        initlist = ArrayList()
        GlobalScope.launch {
            initlist = db.getAll()
            initlist.reversed()
        }
        enableEdgeToEdge()
        binding = ActivityOzoneDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val rv = binding.recyclerView
        rv.layoutManager = LinearLayoutManager(this)
        adapter = Adapterclass(this,datalist)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.aDDjob.setOnClickListener {
            startActivity(Intent(this,OzoneJobsheet::class.java))
        }

        setUpSearch()
        allsheets()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun allsheets() {
        datalist.addAll(initlist)
    }

    private fun setUpSearch() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterlist(query)
                return true
            }



            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.notifyDataSetChanged()
                filterlist(newText)
                return true
            }

        })
        
    }

    private fun filterlist(s: String?) {
        datalist.clear()
        initlist.forEachIndexed{index, ozoneDataObject ->
            if(ozoneDataObject.customerName.contains(s.toString(), ignoreCase = true)||ozoneDataObject.requestNo.contains(s.toString(),ignoreCase = true)){
                datalist.add(ozoneDataObject)
            }
        }
    }


}






