package com.example.damian.countries

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.damian.countries.adapters.CountryAdapter
import com.example.damian.countries.rest.rest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.widget.SearchView
import com.example.damian.countries.model.Country
import java.util.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var searchView: SearchView
    private var data = mutableListOf<Country>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryAdapter = CountryAdapter(ArrayList())
        getData()
        prepareSearchView()
    }

    @SuppressLint("CheckResult")
    private fun getData() {
        rest.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    data = it
                    countryAdapter = CountryAdapter(it)
                    prepareRecyclerView()
                }
    }

    private fun prepareRecyclerView() {
        recyclerView = findViewById(R.id.countries_recycler)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = countryAdapter
        }
    }

    private fun prepareSearchView() {
        searchView = findViewById(R.id.search_bar)
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener {
            countryAdapter.filterDate = data
            countryAdapter.notifyDataSetChanged()
            false
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.setQuery("", false)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        countryAdapter.filter.filter(newText)
        countryAdapter.notifyDataSetChanged()
        return false
    }
}
