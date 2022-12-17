package com.example.werewolf.model;

import android.graphics.Bitmap;

public class Player {
    private Bitmap bitmap;
    private TypePlayer type;
    private Boolean status;

    public Player(TypePlayer type) {
        this.type = type;
        this.status = true;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public TypePlayer getType() {
        return type;
    }

    public void setType(TypePlayer type) {
        this.type = type;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

