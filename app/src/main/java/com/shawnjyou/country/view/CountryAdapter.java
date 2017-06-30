package com.shawnjyou.country.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shawnjyou.country.R;
import com.shawnjyou.country.databinding.CountryItemBinding;
import com.shawnjyou.country.model.Country;
import com.shawnjyou.country.viewmodel.CountryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private List<Country> countries;

    public CountryAdapter() {
        countries = new ArrayList<>();
    }

    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CountryItemBinding countryItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()), R.layout.country_item, viewGroup, false);
        return new ViewHolder(countryItemBinding);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
        final Country country = countries.get(position);
        CountryViewModel countryViewModel = new CountryViewModel(country);
        holder.countryItemBinding.setViewModel(countryViewModel);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CountryItemBinding countryItemBinding;

        public ViewHolder(CountryItemBinding countryItemBinding) {
            super(countryItemBinding.cardview);
            this.countryItemBinding = countryItemBinding;
        }
    }

    public void addItem(Country country) {
        countries.add(country);
        notifyItemInserted(countries.size() - 1);
    }

    public void clearItems() {
        countries.clear();
        notifyDataSetChanged();
    }

}
