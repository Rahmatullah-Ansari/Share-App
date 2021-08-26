package com.example.shareit.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.shareit.databinding.ActivitySplashBinding;
public class splash extends AppCompatActivity {
    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        //binding.iconWelcome.startAnimation(animation);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(splash.this,MainActivity.class));
            finishAffinity();
        },550);
    }
}