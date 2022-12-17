package com.example.werewolf.gameplay;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.werewolf.R;
import com.example.werewolf.constance.Constance;
import com.example.werewolf.databinding.FragmentGamePlayBinding;
import com.example.werewolf.model.GamePlayState;

public class GamePlayFragment extends Fragment {

    private FragmentGamePlayBinding binding;

    private GamePlayViewModel viewModel = new GamePlayViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGamePlayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.startGame();

        //binding

        binding.ava1.setImageBitmap(Constance.listPlayer.get(0).getBitmap() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.card)
                : Constance.listPlayer.get(0).getBitmap());
        binding.ava2.setImageBitmap(Constance.listPlayer.get(1).getBitmap() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.card)
                : Constance.listPlayer.get(1).getBitmap());
        binding.ava3.setImageBitmap(Constance.listPlayer.get(2).getBitmap() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.card)
                : Constance.listPlayer.get(2).getBitmap());
        binding.ava4.setImageBitmap(Constance.listPlayer.get(3).getBitmap() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.card)
                : Constance.listPlayer.get(3).getBitmap());
        binding.ava5.setImageBitmap(Constance.listPlayer.get(4).getBitmap() == null
                ? BitmapFactory.decodeResource(getResources(), R.drawable.card)
                : Constance.listPlayer.get(4).getBitmap());

        binding.ava1.setOnClickListener( v -> {
            viewModel.selectAva(0);
        });
        binding.ava2.setOnClickListener( v -> {
            viewModel.selectAva(1);
        });
        binding.ava3.setOnClickListener( v -> {
            viewModel.selectAva(2);
        });
        binding.ava4.setOnClickListener( v -> {
            viewModel.selectAva(3);
        });
        binding.ava5.setOnClickListener( v -> {
            viewModel.selectAva(4);
        });

        //observer

        viewModel.selectIndex.observe(getViewLifecycleOwner(), index -> setBorderSelect(index));

        viewModel.gameState.observe(getViewLifecycleOwner(), gamePlayState -> {
            if (gamePlayState == GamePlayState.WATTING){
                changeEnableSelect(false);
                new CountDownTimer(5000, 1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btnVote.setText("VÀO LÀNG 00:" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        viewModel.changeState(GamePlayState.COUNTDOWNWEREWOLF);
                    }
                }.start();
            } else if (gamePlayState == GamePlayState.COUNTDOWNPROPHESY) {
                changeEnableSelect(true);
                new CountDownTimer(10000, 1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btnVote.setText("TIÊN TRI 00:" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        if (viewModel.selectIndex.getValue() != -1)
                            Toast.makeText(requireContext(), Constance.listPlayer.get(viewModel.selectIndex.getValue()).getType().toString(), Toast.LENGTH_SHORT).show();
                        viewModel.changeState(GamePlayState.COUNTDOWNHUNTER);
                    }
                }.start();
            } else if (gamePlayState == GamePlayState.COUNTDOWNHUNTER) {
                changeEnableSelect(true);
                new CountDownTimer(10000, 1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btnVote.setText("BẢO VỆ 00:" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        saveLife();
                        viewModel.changeState(GamePlayState.COUNTDOWNVOTE);
                    }
                }.start();
            } else if (gamePlayState == GamePlayState.COUNTDOWNWEREWOLF) {
                changeEnableSelect(true);
                new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btnVote.setText("KẾT LIỄU 00:" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        kill();
                        viewModel.kill();
                        if (viewModel.stillPlaying()){
                            viewModel.changeState(GamePlayState.COUNTDOWNPROPHESY);
                        } else {
                            Bundle bundle = new Bundle();
                            if (viewModel.werewolfWin()) bundle.putString("winner","SÓI");
                            else bundle.putString("winner", "DÂN LÀNG");
                            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_gamePlayFragment_to_winnerFragment, bundle);
                        }
                    }
                }.start();
            } else if (gamePlayState == GamePlayState.COUNTDOWNVOTE) {
                changeEnableSelect(true);
                new CountDownTimer(15000, 1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        binding.btnVote.setText("VOTE 00:" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        kill();
                        viewModel.kill();
                        if (viewModel.stillPlaying()){
                            viewModel.changeState(GamePlayState.WATTING);
                        } else {
                            Bundle bundle = new Bundle();
                            if (viewModel.werewolfWin()) bundle.putString("winner","SÓI");
                            else bundle.putString("winner", "DÂN LÀNG");
                            Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_gamePlayFragment_to_winnerFragment, bundle);
                        }
                    }
                }.start();
            }
        });
    }
    private void changeEnableSelect(boolean enable){
        if (Constance.listPlayer.get(0).getStatus()) {
            binding.ava1.setClickable(enable);
        } else {
            binding.ava1.setClickable(false);
        }
        if (Constance.listPlayer.get(1).getStatus()) {
            binding.ava2.setClickable(enable);
        } else {
            binding.ava2.setClickable(false);
        }
        if (Constance.listPlayer.get(2).getStatus()) {
            binding.ava3.setClickable(enable);
        } else {
            binding.ava3.setClickable(false);
        }
        if (Constance.listPlayer.get(3).getStatus()) {
            binding.ava4.setClickable(enable);
        } else {
            binding.ava4.setClickable(false);
        }
        if (Constance.listPlayer.get(4).getStatus()) {
            binding.ava5.setClickable(enable);
        } else {
            binding.ava5.setClickable(false);
        }
    }

    private void setBorderSelect(int index) {
        if (Constance.listPlayer.get(0).getStatus()) binding.ava1.setBorderWidth(0);
        if (Constance.listPlayer.get(1).getStatus()) binding.ava2.setBorderWidth(0);
        if (Constance.listPlayer.get(2).getStatus()) binding.ava3.setBorderWidth(0);
        if (Constance.listPlayer.get(3).getStatus()) binding.ava4.setBorderWidth(0);
        if (Constance.listPlayer.get(4).getStatus()) binding.ava5.setBorderWidth(0);

        switch(index){
            case 0:
                binding.ava1.setBorderWidth(15);
                binding.ava1.setBorderColor(Color.RED);
                break;
            case 1:
                binding.ava2.setBorderWidth(15);
                binding.ava2.setBorderColor(Color.RED);
                break;
            case 2:
                binding.ava3.setBorderWidth(15);
                binding.ava3.setBorderColor(Color.RED);
                break;
            case 3:
                binding.ava4.setBorderWidth(15);
                binding.ava4.setBorderColor(Color.RED);
                break;
            case 4:
                binding.ava5.setBorderWidth(15);
                binding.ava5.setBorderColor(Color.RED);
                break;
            default:
                break;
        }
    }

    public void kill(){
        int index = viewModel.selectIndex.getValue();
        if (index == -1) return;
        switch(index){
            case 0:
                binding.ava1.setBorderWidth(0);
                binding.ava1.setAlpha(0.5f);
                break;
            case 1:
                binding.ava2.setBorderWidth(0);
                binding.ava2.setAlpha(0.5f);
                break;
            case 2:
                binding.ava3.setBorderWidth(0);
                binding.ava3.setAlpha(0.5f);
                break;
            case 3:
                binding.ava4.setBorderWidth(0);
                binding.ava4.setAlpha(0.5f);
                break;
            case 4:
                binding.ava5.setBorderWidth(0);
                binding.ava5.setAlpha(0.5f);
                break;

        }
    }

    public void saveLife(){
        int index = viewModel.selectIndex.getValue();
        if (index == -1) return;
        if (!Constance.listPlayer.get(index).getStatus()){
            Constance.listPlayer.get(index).setStatus(true);
        }
        switch(index){
            case 0:
                binding.ava1.setBorderWidth(0);
                binding.ava1.setAlpha(1.0f);
                break;
            case 1:
                binding.ava2.setBorderWidth(0);
                binding.ava2.setAlpha(1.0f);
                break;
            case 2:
                binding.ava3.setBorderWidth(0);
                binding.ava3.setAlpha(1.0f);
                break;
            case 3:
                binding.ava4.setBorderWidth(0);
                binding.ava4.setAlpha(1.0f);
                break;
            case 4:
                binding.ava5.setBorderWidth(0);
                binding.ava5.setAlpha(1.0f);
                break;

        }
    }

}