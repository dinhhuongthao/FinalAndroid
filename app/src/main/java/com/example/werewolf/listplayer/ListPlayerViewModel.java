package com.example.werewolf.listplayer;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.werewolf.model.Player;
import com.example.werewolf.model.TypePlayer;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListPlayerViewModel extends ViewModel {

    private MutableLiveData<List<Player>> _listPlayer = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Player>> listPlayer = _listPlayer;
    public ListPlayerViewModel() {
        List<Player> shuffleListPlayer = new ArrayList<>();
        shuffleListPlayer.add(new Player(TypePlayer.WEREWOLF));
        shuffleListPlayer.add(new Player(TypePlayer.VILLAGER));
        shuffleListPlayer.add(new Player(TypePlayer.VILLAGER));
        shuffleListPlayer.add(new Player(TypePlayer.PROPHESY));
        shuffleListPlayer.add(new Player(TypePlayer.HUNTER));
        Collections.shuffle(shuffleListPlayer);
        _listPlayer.postValue(shuffleListPlayer);
    }

    public void setBitmapAt(int index, Bitmap bitmap) {
        this._listPlayer.getValue().get(index).setBitmap(bitmap);
        this._listPlayer.postValue(this._listPlayer.getValue());

    }
}