package com.example.shareit.Adapter;
import static androidx.core.content.FileProvider.getUriForFile;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
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
    public void onBindViewHolder(@NonNull App_adapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.app_name.setText(arrayList.get(position).getApp_name());
        holder.app_size.setText(get_actual_size(arrayList.get(position).getSize()));
        holder.App_icon.setImageDrawable(arrayList.get(position).getIcon());
        holder.share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Share_app(position);
            }
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,"com.example.shareit.provider",new File(arrayList.get(position).getPath())));
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("application/vnd.android.package-archive");
            //intent.setType("plain/text");
            context.startActivity(Intent.createChooser(intent,"Share with").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        });
    }
    private void Share_app(int position) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context,"com.example.shareit.provider",new File(arrayList.get(position).getPath())));
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("application/vnd.android.package-archive");
        //intent.setType("plain/text");
        context.startActivity(Intent.createChooser(intent,"Share with").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
        ImageButton share_app;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            App_icon=itemView.findViewById(R.id.app_icon);
            app_name=itemView.findViewById(R.id.app_name);
            app_size=itemView.findViewById(R.id.app_size);
            share_app=itemView.findViewById(R.id.shareApp);
        }
    }
}
