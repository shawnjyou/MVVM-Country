package com.shawnjyou.country.api;

import com.shawnjyou.country.model.Country;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIService {

    @GET("all")
    Observable<List<Country>> getAllCountry();

}
