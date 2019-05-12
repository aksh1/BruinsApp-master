package com.example.bruins;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private StaffAdapter mStaffAdapter;



    public void setConfig(RecyclerView recyclerView, Context context, List<Staff> staff, List<String> keys) {

        mContext = context;
        mStaffAdapter = new StaffAdapter(staff, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mStaffAdapter);

    }



      class StaffItemView extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mEmail;





        private String key;

        public StaffItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.staff_list_item, parent, false));

            mName = (TextView) itemView.findViewById(R.id.name_txtView);
            mEmail = (TextView) itemView.findViewById(R.id.email_txtView);
        }

        public void bind(Staff staff, String key) {
            mName.setText(staff.getName());
            mEmail.setText(staff.getEmail());
            this.key = key;
        }



    }

    class StaffAdapter extends RecyclerView.Adapter<StaffItemView>{
        public List<Staff> mStaffList;
        private List<String> mKeys;

        public StaffAdapter(List<Staff> mStaffList, List<String> mKeys) {
            this.mStaffList = mStaffList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public StaffItemView onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            return new StaffItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull StaffItemView holder, int position) {
            holder.bind(mStaffList.get(position), mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mStaffList.size();
        }
    }
}
