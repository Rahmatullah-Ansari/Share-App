package com.example.shareit.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.example.shareit.Adapter.App_adapter;
import com.example.shareit.Model.Model_app;
import com.example.shareit.databinding.ActivityMainBinding;

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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerView=binding.recyclerView;
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        }
        Collections.sort(arrayList, Comparator.comparing(model_app -> model_app.getApp_name().toLowerCase()));
        recyclerView.setHasFixedSize(true);
        App_adapter adapter=new App_adapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);
    }
}