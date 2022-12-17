package com.example.werewolf.chooseplayer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChoosePlayerViewModel extends ViewModel {
    private MutableLiveData<Integer> _noticeStartGame = new MutableLiveData<>(0);
    public LiveData<Integer> noticeStartGame = _noticeStartGame;

    void startGame(int numberOfPlayer) {
        if (numberOfPlayer < 5) {
            _noticeStartGame.postValue(1);
        } else {
            _noticeStartGame.postValue(2);
        }
    }
}