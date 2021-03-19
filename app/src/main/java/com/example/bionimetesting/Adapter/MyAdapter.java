package com.example.bionimetesting.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bionimetesting.Post;
import com.example.bionimetesting.R;
import com.example.bionimetesting.RoomDataBase.DataBase;
import com.example.bionimetesting.RoomDataBase.MyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<MyData> mData;
    private Activity activity;
    private AdapterView.OnItemClickListener onItemClickListener;

    public MyAdapter(Activity activity, List<MyData> data){
        this.mData = data;
        this.activity = activity;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myviewholder,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.SiteName.setText(mData.get(position).getSiteName());
        holder.AQI.setText(mData.get(position).getAQI());
        holder.Status.setText(mData.get(position).getStatus());
        holder.O3_8hr.setText(mData.get(position).getO3_8hr());
        holder.PM.setText(mData.get(position).getPM());
        holder.WindSpeed.setText(mData.get(position).getWindSpeed());
        holder.PublishTime.setText(mData.get(position).getPublishTime());
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                deleteData(mData.get(position).getId());
                refreshView();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView SiteName,AQI,Status,O3_8hr,PM,WindSpeed,PublishTime;
        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            SiteName = itemView.findViewById(R.id.SiteName);
            AQI = itemView.findViewById(R.id.AQI);
            Status = itemView.findViewById(R.id.Status);
            O3_8hr = itemView.findViewById(R.id.O3_8hr);
            PM = itemView.findViewById(R.id.PM);
            WindSpeed = itemView.findViewById(R.id.WindSpeed);
            PublishTime = itemView.findViewById(R.id.PublishTime);
        }
    }

    /**更新資料*/
    public void refreshView() {
        new Thread(()->{
            List<MyData> data = DataBase.getInstance(activity).getDataUao().displayAll();
            this.mData = data;
            activity.runOnUiThread(() -> {
                notifyDataSetChanged();
            });
        }).start();
    }
    /**刪除資料*/
    public void deleteData(int position){
        new Thread(()->{
            DataBase.getInstance(activity).getDataUao().deleteData(position);
            activity.runOnUiThread(()->{
                notifyItemRemoved(position);
                refreshView();
            });
        }).start();
    }
}
