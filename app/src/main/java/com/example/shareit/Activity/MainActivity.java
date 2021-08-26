package com.example.shareit.Activity;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.shareit.Adapter.App_adapter;
import com.example.shareit.Model.Model_app;
import com.example.shareit.R;
import com.example.shareit.databinding.ActivityMainBinding;
import java.io.File;
import java.util.ArrayList;
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
        recyclerView=binding.recyclerView;
        arrayList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setHasFixedSize(true);
        adapter=new App_adapter(MainActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
        //get_apps_list
        load_data();
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void load_data() {
        PackageManager packageManager=getApplicationContext().getPackageManager();
        @SuppressLint("QueryPermissionsNeeded") List<ApplicationInfo> arrayList1=packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String name;
        Drawable icon;
        String path;
        long size;
        Model_app app;
        for (ApplicationInfo applicationInfo:arrayList1){
            if ((name=String.valueOf(packageManager.getApplicationLabel(applicationInfo))).isEmpty()){
                name=applicationInfo.packageName;
            }
            icon=packageManager.getApplicationIcon(applicationInfo);
            path=applicationInfo.sourceDir;
            size=new File(applicationInfo.sourceDir).length();
            app=new Model_app(name,icon,path,size);
            arrayList.add(app);
        }
        Collections.sort(arrayList, Comparator.comparing(model_app -> model_app.getApp_name().toLowerCase()));
        adapter.notifyDataSetChanged();
    }
    @Override
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
                filter(newText.toLowerCase());
                return false;
            }
        });
        return true;
    }
    private void filter(String text) {
        List<Model_app> temp = new ArrayList<>();
        if (text.isEmpty()) {
            temp.addAll(arrayList);
        } else{
            for (Model_app data : arrayList) {
                if (data.getApp_name().toLowerCase().contains(text)) {
                    temp.add(data);
                }
            }
    }
        adapter.updateApp(temp);
    }
}