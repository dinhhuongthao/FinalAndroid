package com.example.werewolf.result;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.werewolf.R;
import com.example.werewolf.databinding.FragmentWinnerBinding;

import java.math.BigInteger;

public class WinnerFragment extends Fragment {

    private String winner;
    private FragmentWinnerBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        winner = getArguments().getString("winner");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWinnerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.winnerTxt.setText(winner + "\nCHIẾN THẮNG!");
        binding.btnRestart.setOnClickListener(view1 -> {
            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_winnerFragment_to_startFragment);
        });
    }
}