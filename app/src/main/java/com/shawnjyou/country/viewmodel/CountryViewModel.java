package com.shawnjyou.country.viewmodel;

import android.databinding.BindingAdapter;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.shawnjyou.country.model.Country;

import java.io.InputStream;

public class CountryViewModel {

    private Country country;

    public CountryViewModel(Country country) {
        this.country = country;
    }

    public String getName() {
        return country.getName();
    }

    public String getNativeName() {
        return country.getNativeName();
    }

    public String getImageUrl() {
        return country.getFlag();
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView imageView, String url) {

        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(imageView.getContext())
                .using(Glide.buildStreamModelLoader(Uri.class, imageView.getContext()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
//                .placeholder(R.drawable.image_loading)
//                .error(R.drawable.image_error)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());

        Uri uri = Uri.parse(url);
        requestBuilder
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                // SVG cannot be serialized so it's not worth to cache it
                .load(uri)
                .into(imageView);

    }

}
