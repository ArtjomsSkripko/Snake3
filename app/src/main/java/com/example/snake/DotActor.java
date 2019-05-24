package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

import com.example.snake.actors.PositionedDefaultActor;

public class DotActor extends PositionedDefaultActor {
	
	public static final int DRAW_SIZE = 25;

	public DotActor(int x, int y) {
		super(x, y, DRAW_SIZE, DRAW_SIZE);
	}

	@Override
	public void stylePaint(Paint p) {
		p.setColor(Color.RED);
		p.setStyle(Style.FILL);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(getRectF(), 10, 10, getPaint());
	}

	public void reposition(AbstractGamePanel panel) {
		setPos(randomCoordinatesForPanel(panel.getWidth()), randomCoordinatesForPanel(panel.getHeight()));
	}

	protected int randomCoordinatesForPanel(int max) {
		int multiplier = max / DRAW_SIZE;
		int randomCoordinate = (int) (Math.random() * multiplier);
		return randomCoordinate * DRAW_SIZE;
	}

}
