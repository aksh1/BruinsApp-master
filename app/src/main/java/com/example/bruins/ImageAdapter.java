package com.example.bruins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position)                                                                                                    {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
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

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            imageView = itemView.findViewById(R.id.image_view_upload);

        }
    }

}