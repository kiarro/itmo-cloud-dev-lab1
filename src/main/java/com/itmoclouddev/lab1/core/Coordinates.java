package com.itmoclouddev.lab1.core;

public class Coordinates {
    private float x; //Поле не может быть null
    private float y;

    public Coordinates(float x, float y){
        this.setX(x);
        this.setY(y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
