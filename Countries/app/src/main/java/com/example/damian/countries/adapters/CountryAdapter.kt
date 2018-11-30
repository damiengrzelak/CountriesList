package com.example.damian.countries.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.damian.countries.R
import com.example.damian.countries.model.Country
import com.example.damian.countries.utils.getArrayAsString

class CountryAdapter(var data: MutableList<Country>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>(), Filterable {
    private lateinit var filter: CountryFilter
    var filterDate = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = filterDate.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        filterDate[position].let {
            val item = it
            holder.name.text = item.name
            holder.currencyCode.text = item.currencies.asSequence().filterNotNull().filterNot { it.code.equals(null) }.joinToString { it.code.toString() }
            holder.currencyName.text = item.currencies.asSequence().filterNotNull().filterNot { it.name.equals(null) }.joinToString { it.name.toString() }
            holder.currencySymbol.text = item.currencies.asSequence().filterNotNull().filterNot { it.symbol.equals(null) }.joinToString { it.symbol.toString() }

            holder.phoneNumberCode.text = getArrayAsString(item.phoneNumberCode.toTypedArray(), ",")
            holder.internetDomain.text = getArrayAsString(item.internetDomain.toTypedArray(), ",")
        }
    }

    override fun getFilter(): Filter {
        filter = CountryFilter(filterDate, this)
        return filter
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val currencyName: TextView = view.findViewById(R.id.currency_value)
        val currencyCode: TextView = view.findViewById(R.id.currency_code_value)
        val currencySymbol: TextView = view.findViewById(R.id.currency_symbol_value)
        val phoneNumberCode: TextView = view.findViewById(R.id.phone_number_value)
        val internetDomain: TextView = view.findViewById(R.id.internet_domain_value)
    }
}

class CountryFilter(private var originalList: MutableList<Country>, private var adapter: CountryAdapter) : Filter() {
    private val filteredList = mutableListOf<Country>()

    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        filteredList.clear()
        var text = constraint
        val results = Filter.FilterResults()

        if (text.isNullOrEmpty() || text?.length == 0) {
            filteredList.addAll(originalList)
        } else {
            text = text.toString().toLowerCase()
            for (item in originalList) {
                if (item.name.toLowerCase().contains(text)) {
                    filteredList.add(item)
                }
            }
        }
        results.count = filteredList.size
        results.values = filteredList
        return results
    }

    override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
        adapter.filterDate = results.values as MutableList<Country>
        adapter.notifyDataSetChanged()
    }
}