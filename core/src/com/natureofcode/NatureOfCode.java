package com.natureofcode;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

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
		camera = new OrthographicCamera(1200, 800);
		shapeRenderer = new ShapeRenderer();

		chasers = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			float initSize = (rand.nextFloat() * 40) + 10;
			Vector2 initPos = new Vector2((rand.nextFloat() * camera.viewportWidth) - camera.viewportWidth / 2f,
					(rand.nextFloat() * camera.viewportHeight) - camera.viewportHeight / 2f);
			chasers.add(new Mover(initSize, initPos));
		}
		accelForce = 1000f;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Mover chaser : chasers) {
			float mouseX = MathUtils.map(0,
					width,
					-1 * camera.viewportWidth / 2f,
					camera.viewportWidth / 2f,
					Gdx.input.getX());
			float mouseY = MathUtils.map(0,
					height,
					camera.viewportHeight / 2f,
					-1f * camera.viewportHeight / 2f,
					Gdx.input.getY());
			Vector2 mousePosition = new Vector2(mouseX, mouseY);
			Vector2 dir = mousePosition.sub(chaser.getPosition()).nor().scl(accelForce);
			chaser.setAcceleration(dir);
			chaser.update(Gdx.graphics.getDeltaTime(), camera);
			shapeRenderer.setColor(0.5f, 0.8f, 0.5f, 1f);
			shapeRenderer.circle(chaser.getPosition().x,
					chaser.getPosition().y,
					chaser.getSize());
			shapeRenderer.setColor(0.1f, 0.1f, 0.8f, 1f);
			shapeRenderer.circle(chaser.getPosition().x,
					chaser.getPosition().y,
					chaser.getSize() * 0.9f);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
