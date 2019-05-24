package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.snake.actors.PositionedDefaultActor;

public class ScoreBoard extends PositionedDefaultActor {
	private int score;

	public ScoreBoard(AbstractGamePanel context) {
		super(context.getWidth() - 150, 30);
		this.score = 0;
	}

	@Override
	public void stylePaint(Paint p) {
		p.setTextSize(20);
	}
	
	public void earnPoints(int points) {
		score += points;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText("Score: " + score, getX(), getY(), getPaint());
	}

	public void draw(Canvas canvas, int x, int y, int size) {
		Paint p2 = getPaint();
		p2.setTextSize(size);
		p2.setColor(Color.BLACK);
		canvas.drawText("Score: " + score, x, y, p2);
	}

}
