package com.example.werewolf.listplayer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werewolf.R;
import com.example.werewolf.constance.Constance;
import com.example.werewolf.databinding.FragmentListPlayerBinding;
import com.example.werewolf.databinding.FragmentTakePicBinding;

public class ListPlayerFragment extends Fragment {

    private FragmentListPlayerBinding binding;
    private ListPlayerViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ListPlayerViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentListPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.ava1.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 0);
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_takePicFragment, bundle);
        });

        binding.ava2.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 1);
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_takePicFragment, bundle);
        });

        binding.ava3.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 2);
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_takePicFragment, bundle);
        });

        binding.ava4.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 3);
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_takePicFragment, bundle);
        });

        binding.ava5.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putInt("index", 4);
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_takePicFragment, bundle);
        });

        binding.btnStart.setOnClickListener( view1 -> {
            Constance.listPlayer = viewModel.listPlayer.getValue();
            Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_listPlayerFragment_to_gamePlayFragment);
        });

        //Observer

        viewModel.listPlayer.observe(requireActivity(), value -> {
            if (value.get(0).getBitmap() != null) {
                binding.ava1.setImageBitmap(value.get(0).getBitmap());
            } else {
                binding.ava1.setImageResource(R.drawable.card);
            }
            if (value.get(1).getBitmap() != null) {
                binding.ava2.setImageBitmap(value.get(1).getBitmap());
            } else {
                binding.ava2.setImageResource(R.drawable.card);
            }
            if (value.get(2).getBitmap() != null) {
                binding.ava3.setImageBitmap(value.get(2).getBitmap());
            } else {
                binding.ava3.setImageResource(R.drawable.card);
            }
            if (value.get(3).getBitmap() != null) {
                binding.ava4.setImageBitmap(value.get(3).getBitmap());
            } else {
                binding.ava4.setImageResource(R.drawable.card);
            }
            if (value.get(4).getBitmap() != null) {
                binding.ava5.setImageBitmap(value.get(4).getBitmap());
            } else {
                binding.ava5.setImageResource(R.drawable.card);
            }
        });
    }
}