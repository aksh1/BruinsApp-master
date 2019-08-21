package com.example.bruins.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.example.bruins.R;
import com.example.bruins.Upload;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


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
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewUsername.setText(uploadCurrent.getmUsername());
        holder.textViewDate.setText(uploadCurrent.getDate());


        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .noFade()
//                .resize(500, 400)
//                .centerCrop()
                .into(holder.imageView);

        Log.d("PROFILEPIC", uploadCurrent.getProfilePicture());

        Picasso.get()
                .load(uploadCurrent.getProfilePicture())
                .placeholder(R.mipmap.ic_launcher)
                .noFade()
                .resize(50, 50)
                .centerCrop()
                .into(holder.profilePic);
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
        public CircleImageView profilePic;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            imageView = itemView.findViewById(R.id.image_view_upload);
            profilePic = itemView.findViewById(R.id.profilePic);

        }
    }
}