package com.example.shareit.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.shareit.Adapter.App_adapter;
import com.example.shareit.Model.Model_app;
import com.example.shareit.R;
import com.example.shareit.databinding.ActivityMainBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Model_app> arrayList;
    RecyclerView recyclerView;
    App_adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        create_folder("Apks");
        recyclerView=binding.recyclerView;
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new App_adapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);
        //get_apps_list
        PackageManager packageManager=getApplicationContext().getPackageManager();
        List<ApplicationInfo> arrayList1=packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo:arrayList1){
            String name;
            if ((name=String.valueOf(packageManager.getApplicationLabel(applicationInfo))).isEmpty()){
                name=applicationInfo.packageName;
            }
            Drawable icon=packageManager.getApplicationIcon(applicationInfo);
            String path=applicationInfo.sourceDir;
            long size=new File(applicationInfo.sourceDir).length();
            Model_app app=new Model_app(name,icon,path,size);
            arrayList.add(app);
            adapter.notifyDataSetChanged();
        }
        Collections.sort(arrayList, Comparator.comparing(model_app -> model_app.getApp_name().toLowerCase()));
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }*/
    private void create_folder(String Apks) {
        Dexter.withContext(MainActivity.this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                File folder=new File(Environment.getExternalStorageDirectory()+File.separator+Apks);
                if (!folder.exists() && !folder.isDirectory()){
                    folder.mkdir();
                }
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
}