package com.example.bruins;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bruins.Activities.MainActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Upload> mUploads;

    public ImageAdapter(List<Upload> uploads) {
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Log.d("NAME", uploadCurrent.getName());
        holder.textViewUsername.setText(uploadCurrent.getmUsername());
        holder.textViewDate.setText(uploadCurrent.getDate());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername;
        public TextView textViewName;
        public TextView textViewDate;
        public PhotoView imageView;
        public AdView adView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            imageView = itemView.findViewById(R.id.image_view_upload);
            adView = itemView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setVisibility(View.INVISIBLE);

        }
    }

}