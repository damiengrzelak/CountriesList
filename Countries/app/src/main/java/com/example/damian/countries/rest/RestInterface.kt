package com.example.damian.countries.rest

import com.example.damian.countries.model.Country

import io.reactivex.Observable
import retrofit2.http.GET

interface RestInterface {
    @GET("rest/v2/all?fields=name;currencies;callingCodes;topLevelDomain")
    fun getData(): Observable<MutableList<Country>>
}
