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

	private int windowWidth, windowHeight;
	private float aspectRatio, viewWidth, viewHeight;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Mover> balls;
	private Vector2 gravity;
	
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		aspectRatio = (float) windowHeight / (float) windowWidth;
		viewWidth = 1800;
		viewHeight = viewWidth * aspectRatio;
		camera = new OrthographicCamera(viewWidth, viewHeight);
		shapeRenderer = new ShapeRenderer();

		balls = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			float initSize = (rand.nextFloat() * 40f) + 20f;
			Vector2 initPos = new Vector2((rand.nextFloat() * camera.viewportWidth) - camera.viewportWidth / 2f,
					(rand.nextFloat() * camera.viewportHeight) - camera.viewportHeight / 2f);
			balls.add(new Mover(initSize, initPos, new Vector2(MathUtils.random(500f) - 250f, 0)));
		}

		gravity = new Vector2(0f, -10f);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0.4f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (Mover ball : balls) {
			ball.applyForce(gravity);
			ball.update(Gdx.graphics.getDeltaTime(), camera);
			drawMover(shapeRenderer, ball);
		}
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}

	private void drawMover(ShapeRenderer shapeRenderer, Mover mover) {
		shapeRenderer.setColor(0.7f, 0.7f, 0.8f, 1f);
		shapeRenderer.circle(mover.getPosition().x,
				mover.getPosition().y,
				mover.getSize());
		shapeRenderer.setColor(0.4f, 0.8f, 0.6f, 1f);
		shapeRenderer.circle(mover.getPosition().x,
				mover.getPosition().y,
				mover.getSize() * 0.9f);
	}

	private Vector2 getMouseVector() {
		float mouseX = MathUtils.map(0,
				windowWidth,
				-1 * camera.viewportWidth / 2f,
				camera.viewportWidth / 2f,
				Gdx.input.getX());
		float mouseY = MathUtils.map(0,
				windowHeight,
				camera.viewportHeight / 2f,
				-1f * camera.viewportHeight / 2f,
				Gdx.input.getY());
		return new Vector2(mouseX, mouseY);
	}
}
