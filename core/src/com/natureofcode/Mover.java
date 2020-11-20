package com.natureofcode;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Mover {

    private Vector2 position, velocity, acceleration;
    private float size;

    public Mover(float size, Vector2 initPos) {
        this.position = initPos;
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        this.size = size;
    }

    public Mover(float size, Vector2 initPos, Vector2 initVel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = new Vector2(0, 0);
        this.size = size;
    }

    public Mover(float size, Vector2 initPos, Vector2 initVel, Vector2 initAccel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = initAccel;
        this.size = size;
    }

    public void update(float deltaTime, OrthographicCamera camera) {
        velocity.add(acceleration.cpy().scl(deltaTime));
        position.add(velocity.cpy().scl(deltaTime));
        this.checkBounds(camera);
    }

    private void checkBounds(OrthographicCamera camera) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;
//
//        if (position.x < -1f * width / 2f || position.x > width / 2f
//                || position.y < -1f * height / 2f || position.y > height / 2f) {
//            camera.viewportWidth *= 2;
//            camera.viewportHeight *= 2;
//            camera.update();
//        }

        if (position.x < -1f * width / 2f || position.x > width / 2f) {
            velocity = new Vector2(-1f * velocity.x, velocity.y);
        }
        if (position.y < -1f * height / 2f || position.y > height / 2f) {
            velocity = new Vector2(velocity.x, -1f * velocity.y);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setPosition(Vector2 newPosition) {
        this.position = newPosition;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public float getSize() {
        return size;
    }
}