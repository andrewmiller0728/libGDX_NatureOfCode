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
	private Liquid water;
	
	@Override
	public void create () {
		windowWidth = Gdx.graphics.getWidth();
		windowHeight = Gdx.graphics.getHeight();
		aspectRatio = (float) windowHeight / (float) windowWidth;
		viewWidth = 450;
		viewHeight = viewWidth * aspectRatio;
		camera = new OrthographicCamera(viewWidth, viewHeight);
		shapeRenderer = new ShapeRenderer();

		balls = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			float initMass = (rand.nextFloat() * 8f) + 2f;
			Vector2 initPos = new Vector2((rand.nextFloat() * camera.viewportWidth) - camera.viewportWidth / 2f,
					(rand.nextFloat() * camera.viewportHeight / 2f)
			);
			balls.add(new Mover(initMass, initPos, new Vector2(0, 0)));
		}

		water = new Liquid(
				new Vector2(
						-1f * camera.viewportWidth / 2f,
						-1f * camera.viewportHeight / 2f),
				new Vector2(
						camera.viewportWidth,
						camera.viewportHeight / 3f),
				1f,
				0.5f
		);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0.4f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		drawLiquid(shapeRenderer, water);
		for (Mover ball : balls) {
			ball.applyGravity(30f);
			if (ball.isInside(water)) {
				ball.applyDrag(water);
			}
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
				mover.getMass() * 2f
		);
		shapeRenderer.setColor(0.4f, 0.8f, 0.6f, 1f);
		shapeRenderer.circle(mover.getPosition().x,
				mover.getPosition().y,
				mover.getMass() * 2f * 0.9f
		);
	}

	private void drawLiquid(ShapeRenderer shapeRenderer, Liquid liquid) {
		shapeRenderer.setColor(0.3f, 0.3f, 1f, 0.5f);
		shapeRenderer.rect(
				liquid.getPosition().x,
				liquid.getPosition().y,
				liquid.getSize().x,
				liquid.getSize().y
		);
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
