package com.natureofcode;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;

public class Mover {

    private Vector2D position, velocity, acceleration;
    private float size;

    public Mover(float size, Vector2D initPos) {
        this.position = initPos;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
        this.size = size;
    }

    public Mover(float size, Vector2D initPos, Vector2D initVel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = new Vector2D(0, 0);
        this.size = size;
    }

    public Mover(float size, Vector2D initPos, Vector2D initVel, Vector2D initAccel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = initAccel;
        this.size = size;
    }

    public void update(float deltaTime, Camera camera) {
        Vector2D deltaVelocity = acceleration.getCopy();
        deltaVelocity.mult(deltaTime / size);
        velocity.add(deltaVelocity);

        Vector2D deltaPosition = velocity.getCopy();
        deltaPosition.mult(deltaTime);
        position.add(deltaPosition);

        this.checkBounds(camera);
    }

    private void checkBounds(Camera camera) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        if (position.getX() < -1f * width / 2f || position.getX() > width / 2f) {
            velocity = new Vector2D(-1f * velocity.getX(), velocity.getY());
        }
        if (position.getY() < -1f * height / 2f || position.getY() > height / 2f) {
            velocity = new Vector2D(velocity.getX(), -1f * velocity.getY());
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public void setPosition(Vector2D newPosition) {
        this.position = newPosition;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public float getSize() {
        return size;
    }
}