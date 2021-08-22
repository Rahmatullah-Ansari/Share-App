package com.example.shareit.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.BuildConfig;

import com.example.shareit.Model.Model_app;
import com.example.shareit.R;

import java.io.File;
import java.util.ArrayList;

public class App_adapter extends RecyclerView.Adapter<App_adapter.ViewHolder> {
    Context context;
    ArrayList<Model_app> arrayList;
    public App_adapter(Context context, ArrayList<Model_app> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public App_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.app_row,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull App_adapter.ViewHolder holder, int position) {
        holder.app_name.setText(arrayList.get(position).getApp_name());
        holder.app_size.setText(get_actual_size(arrayList.get(position).getSize()));
        holder.App_icon.setImageDrawable(arrayList.get(position).getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
            }
        });
    }
    private String get_actual_size(long size) {
        String app_size;
        if (size<1024){
            app_size=String.format(context.getString(R.string.app_size_b),(double)size);
        }
        else if (size<Math.pow(1024,2)){
            app_size=String.format(context.getString(R.string.app_size_kib),(double)size/1024);
        }
        else if (size<Math.pow(1024,3)){
            app_size=String.format(context.getString(R.string.app_size_mib),(double)size/Math.pow(1024,2));
        }
        else{
            app_size=String.format(context.getString(R.string.app_size_gib),(double)size/Math.pow(1023,3));
        }
        return app_size;
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView App_icon;
        TextView app_name,app_size;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            App_icon=itemView.findViewById(R.id.app_icon);
            app_name=itemView.findViewById(R.id.app_name);
            app_size=itemView.findViewById(R.id.app_size);
        }
    }
}
