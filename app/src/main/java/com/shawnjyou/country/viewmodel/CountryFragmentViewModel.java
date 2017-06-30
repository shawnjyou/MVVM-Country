package com.shawnjyou.country.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.shawnjyou.country.api.APIClient;
import com.shawnjyou.country.model.Country;
import com.shawnjyou.country.view.CompletedListener;
import com.shawnjyou.country.view.CountryAdapter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CountryFragmentViewModel {

    CountryAdapter countryAdapter;
    CompletedListener completedListener;
    public ObservableField<Integer> contentViewVisibility;
    public ObservableField<Integer> progressBarVisibility;

    public CountryFragmentViewModel(CountryAdapter countryAdapter, CompletedListener completedListener) {
        this.countryAdapter = countryAdapter;
        this.completedListener = completedListener;
        init();
        getCountry();
    }

    private void init() {
        contentViewVisibility = new ObservableField<>();
        progressBarVisibility = new ObservableField<>();
        contentViewVisibility.set(View.GONE);
        progressBarVisibility.set(View.VISIBLE);
    }

    private void getCountry() {
        Observer observer = new Observer<List<Country>>() {
            @Override
            public void onSubscribe(Disposable d) {
                hideAllView();
                progressBarVisibility.set(View.VISIBLE);
            }

            @Override
            public void onNext(List<Country> countries) {
                for (Country country:countries) {
                    countryAdapter.addItem(country);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                contentViewVisibility.set(View.VISIBLE);
                progressBarVisibility.set(View.INVISIBLE);
                completedListener.onCompleted();
            }
        };
        APIClient.getInstance().getCountry(observer);
    }

    private void hideAllView() {
        contentViewVisibility.set(View.INVISIBLE);
        progressBarVisibility.set(View.INVISIBLE);
    }

    public void refresh() {
        getCountry();
    }

}
