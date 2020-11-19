package com.natureofcode;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Random;

public class NatureOfCode extends ApplicationAdapter {

	private int width, height;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Mover> chasers;
	private float accelForce;
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(1920, 1080);
		shapeRenderer = new ShapeRenderer();

		chasers = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			float initSize = (rand.nextFloat() * 30) + 10;
			Vector2D initPos = new Vector2D((rand.nextFloat() * camera.viewportWidth) - camera.viewportWidth / 2f,
					(rand.nextFloat() * camera.viewportHeight) - camera.viewportHeight / 2f);
			chasers.add(new Mover(initSize, initPos));
		}
		accelForce = 10000f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Mover chaser : chasers) {
			Vector2D mousePosition = new Vector2D(Gdx.input.getX() - camera.viewportWidth / 2f,
					camera.viewportHeight / 2f - Gdx.input.getY());
			Vector2D dir = Vector2D.sub(mousePosition, chaser.getPosition());
			dir.normalize();
			dir.mult(accelForce);
			chaser.setAcceleration(dir);
			chaser.update(Gdx.graphics.getDeltaTime(), camera);
			shapeRenderer.setColor(0f, 0f, 0f, 1f);
			shapeRenderer.circle(chaser.getPosition().getX(), chaser.getPosition().getY(), chaser.getSize());
			shapeRenderer.setColor(0.8f, 0.8f, 0.8f, 1f);
			shapeRenderer.circle(chaser.getPosition().getX(), chaser.getPosition().getY(), chaser.getSize() - 2);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
