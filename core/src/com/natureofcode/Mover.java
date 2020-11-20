package com.natureofcode;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Mover {

    private Vector2 position, velocity, acceleration;
    private float size, mass;

    public Mover(float size, Vector2 initPos) {
        this.position = initPos;
        this.velocity = Vector2.Zero.cpy();
        this.acceleration = Vector2.Zero.cpy();
        this.size = size;
        this.mass = size / 2;
    }

    public Mover(float size, Vector2 initPos, Vector2 initVel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = Vector2.Zero.cpy();
        this.size = size;
    }

    public Mover(float size, Vector2 initPos, Vector2 initVel, Vector2 initAccel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = initAccel;
        this.size = size;
    }

    public void update(float deltaTime, OrthographicCamera camera) {
        this.update(deltaTime, camera, false);
    }

    public void update(float deltaTime, OrthographicCamera camera, boolean expandBounds) {
        velocity.add(acceleration.cpy().scl(deltaTime));
        position.add(velocity.cpy().scl(deltaTime));
        if (expandBounds) {
            this.expandBounds(camera);
        }
        else {
            this.bounceBounds(camera);
        }
    }

    private void bounceBounds(OrthographicCamera camera) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        if (position.x < -1f * width / 2f || position.x > width / 2f) {
            velocity.scl(-1f, 1);
        }
        if (position.y < -1f * height / 2f || position.y > height / 2f) {
            velocity.scl(1, -1);
        }
    }

    private void expandBounds(OrthographicCamera camera) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;
        float aspectRatio = height / width;

        if (position.x < -1f * width / 2f || position.x > width / 2f) {
            float offset = Math.abs(position.x - width / 2f);
            camera.viewportWidth += offset * 2;
            camera.viewportHeight = camera.viewportWidth * aspectRatio;
            camera.update();
        }
        if (position.y < -1f * height / 2f || position.y > height / 2f) {
            float offset = Math.abs(position.y - height / 2f);
            camera.viewportHeight += offset * 2;
            camera.viewportWidth = camera.viewportHeight / aspectRatio;
            camera.update();
        }
    }

    public void applyForce(Vector2 force) {
        acceleration.add(force);
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