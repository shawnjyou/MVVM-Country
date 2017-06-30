package com.shawnjyou.country.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shawnjyou.country.R;
import com.shawnjyou.country.databinding.FragmentCountryBinding;
import com.shawnjyou.country.viewmodel.CountryFragmentViewModel;

public class CountryFragment extends Fragment implements CompletedListener, SwipeRefreshLayout.OnRefreshListener{

    FragmentCountryBinding fragmentCountryBinding;
    CountryFragmentViewModel countryFragmentViewModel;
    CountryAdapter countryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_country, container, false);
        fragmentCountryBinding = FragmentCountryBinding.bind(contentView);
        init();
        return contentView;
    }

    private void init() {
        countryAdapter = new CountryAdapter();
        fragmentCountryBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentCountryBinding.recyclerView.setAdapter(countryAdapter);
        fragmentCountryBinding.swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        fragmentCountryBinding.swipeRefreshLayout.setOnRefreshListener(this);
        countryFragmentViewModel = new CountryFragmentViewModel(countryAdapter, this);
        fragmentCountryBinding.setViewModel(countryFragmentViewModel);
    }

    @Override
    public void onRefresh() {
        countryAdapter.clearItems();
        countryFragmentViewModel.refresh();
    }

    @Override
    public void onCompleted() {
        if (fragmentCountryBinding.swipeRefreshLayout.isRefreshing()) {
            fragmentCountryBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
