package com.natureofcode;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class Mover {

    private Vector2 position, velocity, acceleration;
    private float mass;

    public Mover(float mass, Vector2 initPos) {
        this.position = initPos;
        this.velocity = new Vector2();
        this.acceleration = new Vector2();
        this.mass = mass;
    }

    public Mover(float mass, Vector2 initPos, Vector2 initVel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = new Vector2();
        this.mass = mass;
    }

    public Mover(float mass, Vector2 initPos, Vector2 initVel, Vector2 initAccel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = initAccel;
        this.mass = mass;
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
        acceleration = Vector2.Zero.cpy();
    }

    private void bounceBounds(OrthographicCamera camera) {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        if (position.x < -1f * width / 2f) {
            velocity.scl(-1f, 1);
            position.x = -1f * width / 2f;
        }
        else if (position.x > width / 2f) {
            velocity.scl(-1f, 1);
            position.x = width / 2f;
        }

        if (position.y < -1f * height / 2f) {
            velocity.scl(1, -1);
            position.y = -1f * height / 2f;
        }
        else if (position.y > height / 2f) {
            velocity.scl(1, -1);
            position.y = height / 2f;
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
        acceleration.add(force.cpy().scl(1f / mass));
    }

    public void applyGravity(float mag) {
        acceleration.add(Vector2.Y.cpy().scl(-1f * mag));
    }

    public void applyFriction(float cof) {
        float normal = acceleration.len();
        Vector2 friction = velocity.cpy().scl(-1f).nor().scl(cof * normal);
        this.applyForce(friction);
    }

    public void applyDrag(Liquid liquid) {
        float coefficient = (-0.5f)
                * liquid.getDensity()
                * velocity.cpy().len2()
                * liquid.getCod();
        Vector2 normalVelocity = velocity.cpy().nor();
        this.applyForce(normalVelocity.scl(coefficient));
    }

    public boolean isInside(Liquid liquid) {
        return this.position.x > liquid.getPosition().x
                && this.getPosition().x < liquid.getPosition().x + liquid.getSize().x
                && this.getPosition().y > liquid.getPosition().y
                && this.getPosition().y < liquid.getPosition().y + liquid.getSize().y;
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

    public float getMass() {
        return mass;
    }
}