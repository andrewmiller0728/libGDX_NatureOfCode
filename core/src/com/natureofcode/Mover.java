package com.natureofcode;

import com.badlogic.gdx.Gdx;

public class Mover {

    Vector2D position, velocity, acceleration;

    public Mover(Vector2D initPos) {
        this.position = initPos;
        this.velocity = new Vector2D(0, 0);
        this.acceleration = new Vector2D(0, 0);
    }

    public Mover(Vector2D initPos, Vector2D initVel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = new Vector2D(0, 0);
    }

    public Mover(Vector2D initPos, Vector2D initVel, Vector2D initAccel) {
        this.position = initPos;
        this.velocity = initVel;
        this.acceleration = initAccel;
    }

    public void update(float deltaTime) {
        Vector2D deltaVelocity = acceleration.getCopy();
        deltaVelocity.mult(deltaTime);
        velocity.add(deltaVelocity);

        Vector2D deltaPosition = velocity.getCopy();
        deltaPosition.mult(deltaTime);
        position.add(deltaPosition);

        this.checkBounds();
    }

    private void checkBounds() {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        if (position.getX() < 0) {
            position.setX(width + position.getX());
        }
        if (position.getX() > width) {
            position.setX(position.getX() - width);
        }
        if (position.getY() < 0) {
            position.setY(height + position.getY());
        }
        if (position.getY() > height) {
            position.setY(position.getY() - height);
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
}