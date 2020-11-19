package com.natureofcode;

import com.badlogic.gdx.graphics.Camera;

import java.util.Random;

public class Walker extends Mover {

    private float stepSize;
    private float factor;
    private float r, g, b, a;

    public Walker(float size, Vector2D initPos, float stepSize, float factor) {
        super(size, initPos);
        this.stepSize = stepSize;
        this.factor = factor;

        Vector2D initVel = Vector2D.getRandomVector();
        initVel.normalize();
        initVel.mult(this.stepSize);
        super.setVelocity(initVel);

        r = 1f;
        g = 1f;
        b = 1f;
        a = 1f;
    }

    @Override
    public void update(float deltaTime, Camera camera) {
        super.update(deltaTime, camera);
        changePath();
        walkColors();
    }

    private void changePath() {
        Random random = new Random();
        float deltaTheta = (float) ((random.nextDouble() * (Math.PI / 2f)) - Math.PI / 4f);
        super.getVelocity().normalize();
        super.getVelocity().rotate(deltaTheta);
        super.getVelocity().mult(stepSize);
    }

    private void walkColors() {
        r = walkNumber(r);
        g = walkNumber(g);
        b = walkNumber(b);
    }

    private void walkAlpha() {
        a = walkNumber(a);
    }

    private float walkNumber(float c) {
        Random random = new Random();
        c += (random.nextFloat() - 0.5f) * factor;
        c = Math.abs(c % 1);
        return c;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }
}
