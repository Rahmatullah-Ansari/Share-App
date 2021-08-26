package com.example.shareit.Adapter;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shareit.Model.Model_app;
import com.example.shareit.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
public class App_adapter extends RecyclerView.Adapter<App_adapter.ViewHolder>{
    Context context;
    List<Model_app> arrayList;
    List<Model_app> searched;
    public App_adapter(Context context, ArrayList<Model_app> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.searched=new ArrayList<>(arrayList);
    }
    @NonNull
    @Override
    public App_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.app_row, parent, false));
    }
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull App_adapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.app_name.setText(arrayList.get(position).getApp_name());
        holder.app_size.setText(get_actual_size(arrayList.get(position).getSize()));
        holder.App_icon.setImageDrawable(arrayList.get(position).getIcon());
        holder.share_app.setOnClickListener(view -> Share_app(position));
        holder.save_app.setOnClickListener(view -> Dexter.withContext(context).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        File folder=new File(Environment.getExternalStorageDirectory()+File.separator+"apk");
        if (!folder.exists() && !folder.isDirectory()){
            if (folder.mkdir()){
                Toast.makeText(context, "Folder created!", Toast.LENGTH_SHORT).show();
            }
        }
        Uri uri= Uri.parse(arrayList.get(position).getPath());
        File source=new File(String.valueOf(uri));
        File des=new File(Environment.getExternalStorageDirectory()+File.separator+"apk"+File.separator+arrayList.get(position).getApp_name()+".apk");
        try{
            Files.copy(source.toPath(),des.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Toast.makeText(context, "saved at location :-"+Environment.getExternalStorageDirectory()+File.separator+"apk", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Failed due to :-"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
        permissionToken.continuePermissionRequest();
    }
}).check());
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
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView App_icon;
        TextView app_name,app_size;
        ImageButton share_app,save_app;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            App_icon=itemView.findViewById(R.id.app_icon);
            app_name=itemView.findViewById(R.id.app_name);
            app_size=itemView.findViewById(R.id.app_size);
            share_app=itemView.findViewById(R.id.shareApp);
            save_app=itemView.findViewById(R.id.save_app);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateApp(List<Model_app> list){
        arrayList=list;
        notifyDataSetChanged();
    }
}
