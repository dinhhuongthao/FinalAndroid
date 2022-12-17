package com.example.werewolf.gameplay;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.example.werewolf.constance.Constance;
import com.example.werewolf.model.GamePlayState;
import com.example.werewolf.model.TypePlayer;

import java.util.List;

public class GamePlayViewModel extends ViewModel {
    private MutableLiveData<GamePlayState> _gameState = new MutableLiveData<>(GamePlayState.IDE);
    public LiveData<GamePlayState> gameState = _gameState;

    private MutableLiveData<Integer> _selectIndex = new MutableLiveData<>(-1);
    public LiveData<Integer> selectIndex = _selectIndex;

    public void startGame(){
        _gameState.postValue(GamePlayState.WATTING);
    }

    public void changeState(GamePlayState state){
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                _gameState.postValue(state);
            }
        }.start();
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 20);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
        _selectIndex.postValue(-1);
    }


    public boolean stillPlaying(){
        int werewolf = 0;
        int villager = 0;
        for (int i = 0; i < Constance.listPlayer.size(); i++){
            if (Constance.listPlayer.get(i).getStatus() && Constance.listPlayer.get(i).getType() == TypePlayer.WEREWOLF){
                werewolf += 1;
            } else if (Constance.listPlayer.get(i).getStatus()) {
                villager += 1;
            }
        }
        System.out.println(werewolf+ " "+ villager);
        return (werewolf > 0) && (villager > werewolf);
    }

    public boolean werewolfWin(){
        int werewolf = 0;
        for (int i = 0; i < Constance.listPlayer.size(); i++){
            if (Constance.listPlayer.get(i).getStatus() && Constance.listPlayer.get(i).getType() == TypePlayer.WEREWOLF){
                werewolf += 1;
            }
        }
        return werewolf > 0;
    }

    public void selectAva(int index){
        _selectIndex.postValue(index);
    }

    public void kill(){
        if (_selectIndex.getValue() == -1) return;
        Constance.listPlayer.get(_selectIndex.getValue()).setStatus(false);
    }
}