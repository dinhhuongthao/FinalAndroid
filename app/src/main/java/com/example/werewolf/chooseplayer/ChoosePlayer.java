package com.example.werewolf.chooseplayer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.werewolf.R;
import com.example.werewolf.databinding.FragmentChoosePlayerBinding;

import java.util.Objects;

public class ChoosePlayer extends Fragment {

    private FragmentChoosePlayerBinding binding;

    private ChoosePlayerViewModel viewModel = new ChoosePlayerViewModel();

    public static ChoosePlayer newInstance() {
        return new ChoosePlayer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentChoosePlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup
        binding.startBtn.setOnClickListener(view1 -> viewModel.startGame(Integer.parseInt(String.valueOf(binding.numberOfPlayerEdt.getText()))));

        //observer
        viewModel.noticeStartGame.observe(requireActivity(), option -> {
            switch (option) {
                case 1:
                    Toast.makeText(requireContext(), "Number of player should greater or equal than 5", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_choosePlayer_to_listPlayerFragment);
                    break;
            }
        });
    }
}