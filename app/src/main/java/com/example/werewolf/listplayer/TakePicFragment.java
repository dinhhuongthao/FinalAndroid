package com.example.werewolf.listplayer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.werewolf.databinding.FragmentTakePicBinding;

public class TakePicFragment extends Fragment {

    private ListPlayerViewModel viewModel;
    private FragmentTakePicBinding binding;
    private int index;

    ActivityResultLauncher<Intent> activityResultLauncher;
    public TakePicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ListPlayerViewModel.class);
        index = getArguments().getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTakePicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Bundle bundle = result.getData().getExtras();
                        Bitmap bitmap = (Bitmap) bundle.get("data");
                        binding.ava.setImageBitmap(bitmap);
                        viewModel.setBitmapAt(index, bitmap);
                    }
                }
        );

        switch (viewModel.listPlayer.getValue().get(index).getType()){
            case VILLAGER:
                binding.label.setText("Dân làng");
                break;
            case HUNTER:
                binding.label.setText("Thợ săn");
                break;
            case PROPHESY:
                binding.label.setText("Tiên tri");
                break;
            case WEREWOLF:
                binding.label.setText("Sói");
                break;
        }

        binding.btnTakePic.setOnClickListener(view1 -> {
            Intent openCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (openCam.resolveActivity(getActivity().getPackageManager()) != null) {
                activityResultLauncher.launch(openCam);
            } else {
                Toast.makeText(requireActivity(), "Can't open camera", Toast.LENGTH_SHORT);
            }
        });

        binding.btnConfirm.setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });
    }
}