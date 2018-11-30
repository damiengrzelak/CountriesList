package com.example.damian.countries.model

import com.google.gson.annotations.SerializedName

data class Country(
        @SerializedName("name")
        val name: String,
        @SerializedName("currencies")
        val currencies: List<Currency>,
        @SerializedName("callingCodes")
        val phoneNumberCode: ArrayList<String>,
        @SerializedName("topLevelDomain")
        val internetDomain: List<String>)