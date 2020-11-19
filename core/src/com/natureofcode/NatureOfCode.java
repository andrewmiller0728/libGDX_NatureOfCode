package com.natureofcode;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;

import java.util.ArrayList;
import java.util.Random;

public class NatureOfCode extends ApplicationAdapter {

	private SpriteBatch batch;
	private BitmapFont font;
	private String headerText;
	private ShapeRenderer shapeRenderer;

	private int width, height;

	private float walkerStepSize;
	private float walkerFactor;
	private float walkerRadius;
	private Walker walker;

	ArrayList<float[]> colorLog;
	ArrayList<float[]> positionLog;
	
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		walkerStepSize = 800f;
		walkerFactor = 0.05f;
		walkerRadius = 10f;
		walker = new Walker(new Vector2D(width / 2f, height / 2f),
				walkerStepSize,
				walkerFactor);

		colorLog = new ArrayList<>();
		positionLog = new ArrayList<>();

		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("consolas_sz50.fnt"));
		headerText = String.format("Step Size = %.1f; Factor = %.4f",
				walkerStepSize,
				walkerFactor);
		shapeRenderer = new ShapeRenderer();

		// Pre-render first frames
		for (int i = 0; i < 100000; i++) {
			logWalkerData();
			walker.update(0.016f);
		}
	}

	@Override
	public void render () {
//		logWalkerData();
//		walker.update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (int i = 0; i < colorLog.size(); i++) {
			shapeRenderer.setColor(colorLog.get(i)[0],
					colorLog.get(i)[1],
					colorLog.get(i)[2],
					colorLog.get(i)[3]);
			shapeRenderer.circle(Math.round(positionLog.get(i)[0]),
					Math.round(positionLog.get(i)[1]),
					walkerRadius);
		}
		shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.25f);
		shapeRenderer.rect(0f, height - 150f, width / 2f + 20f, 150f);
		shapeRenderer.end();

		batch.begin();
		font.draw(batch, headerText, 10f, height - 10f);
		font.draw(batch,
				String.format("Radius = %.0f; Steps = %d",
						walkerRadius,
						colorLog.size()),
				10f,
				height - 70f);
		batch.end();
	}
	
	@Override
	public void dispose () {
		shapeRenderer.dispose();
		batch.dispose();
	}

	private void logWalkerData() {
		float[] currColors = new float[4];
		currColors[0] = walker.getR();
		currColors[1] = walker.getG();
		currColors[2] = walker.getB();
		currColors[3] = walker.getA();
		colorLog.add(currColors);
		float[] currPos = new float[2];
		currPos[0] = walker.getPosition().getX();
		currPos[1] = walker.getPosition().getY();
		positionLog.add(currPos);
	}
}
