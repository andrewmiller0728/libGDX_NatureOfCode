package com.natureofcode;

import java.util.Random;

public class Vector2D {

    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // TODO: I suspect this doesn't work...
    public static Vector2D getRandomVector() {
        Random random = new Random();
        float randX = (random.nextFloat() * Float.MAX_VALUE) - (Float.MAX_VALUE / 2);
        float randY = (random.nextFloat() * Float.MAX_VALUE) - (Float.MAX_VALUE / 2);
        return new Vector2D(randX, randY);
    }

    public void add(Vector2D that) {
        this.x += that.getX();
        this.y += that.getY();
    }

    public void sub(Vector2D that) {
        this.x -= that.getX();
        this.y -= that.getY();
    }

    public void mult(float a) {
        this.x *= a;
        this.y *= a;
    }

    public void div(float a) {
        this.x /= a;
        this.y /= a;
    }

    public float mag() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public void normalize() {
        float mag = this.mag();
        if (mag != 0) {
            this.div(mag);
        }
    }

    // Theta should be in radians
    public void rotate(float theta) {
        x = (float) (x * Math.cos(theta) - y * Math.sin(theta));
        y = (float) (x * Math.sin(theta) + y * Math.cos(theta));
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2D getCopy() {
        return new Vector2D(this.x, this.y);
    }


}
