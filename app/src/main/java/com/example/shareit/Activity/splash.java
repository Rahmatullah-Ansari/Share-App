package com.example.shareit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.shareit.R;
import com.example.shareit.databinding.ActivitySplashBinding;

public class splash extends AppCompatActivity {
    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        binding.iconWelcome.startAnimation(animation);
        new Handler().postDelayed(() -> {
            animation.cancel();
            startActivity(new Intent(splash.this,MainActivity.class));
            finish();
        },1500);
    }
}