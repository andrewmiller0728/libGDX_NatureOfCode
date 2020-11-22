package com.natureofcode;

import com.badlogic.gdx.math.Vector2;

public class Liquid {
    private Vector2 position, size;
    private float density, cod;

    public Liquid(Vector2 position, Vector2 size, float density, float cod) {
        this.position = position;
        this.size = size;
        this.density = density;
        this.cod = cod;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public float getDensity() {
        return density;
    }

    public float getCod() {
        return cod;
    }
}