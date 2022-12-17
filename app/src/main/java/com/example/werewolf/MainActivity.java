package com.example.werewolf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.werewolf.listplayer.ListPlayerViewModel;

public class MainActivity extends AppCompatActivity {

    private ListPlayerViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(ListPlayerViewModel.class);

    }
}