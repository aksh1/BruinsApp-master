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


public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    private Context mContext;
    private List<Staff> mStaff;

    public StaffAdapter(Context context, List<Staff> staff) {
        mContext = context;
        mStaff = staff;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.staff_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Staff staffCurrent = mStaff.get(position);
        holder.textViewName.setText(staffCurrent.getName());
        holder.textViewEmail.setText(staffCurrent.getEmail());


    }

    @Override
    public int getItemCount() {
        return mStaff.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewEmail;
        public TextView textViewName;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewEmail = itemView.findViewById(R.id.email_txtView);
            textViewName = itemView.findViewById(R.id.name_txtView);
        }
    }
}