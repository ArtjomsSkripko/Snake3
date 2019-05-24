package com.example.snake;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.snake.actors.SimpleMovingActor;

public class SnakeActor extends SimpleMovingActor {

	public static final int DRAW_SIZE = 25;
	public static final int STEP = 25;
	public ArrayList<Point> tailPos;

	public SnakeActor(int x, int y) {
		super(x, y, DRAW_SIZE, DRAW_SIZE);
		getVelocity().stop().setXDirection(Velocity.DIRECTION_RIGHT).setXSpeed(STEP);
		tailPos = new ArrayList<>();
		tailPos.add(new Point(x - this.getWidth(), y));
		tailPos.add(new Point(x - this.getWidth() * 2, y));
	}

	@Override
	public void stylePaint(Paint p) {
		p.setColor(Color.GREEN);
		p.setStyle(Style.FILL);
	}

	@Override
	public void draw(Canvas canvas) {
		getPaint().setColor(Color.GREEN);
		canvas.drawRect(getRect(), getPaint());
		for (Point p : tailPos) {
			Rect r = new Rect(p.x, p.y, p.x + this.getWidth(), p.y + this.getHeight());
			canvas.drawRect(r, getPaint());
		}
	}

	public void move() {
		if (this.isEnabled()) {
			int headX = getPoint().x;
			int headY = getPoint().y;
			for (int x = tailPos.size() - 1; x > 0; x--) {
				tailPos.get(x).set(tailPos.get(x - 1).x, tailPos.get(x - 1).y);
			}
			tailPos.get(0).set(headX, headY);
			super.move();
		}
	}

	public void grow() {
		this.tailPos.add(new Point(getX(), getY()));
	}

	public boolean checkBoundsCollision(AbstractGamePanel panel) {

		//finish game when snake ate itself
		ArrayList<Point> tailPos = this.tailPos;
		for (int i = 0; i < tailPos.size(); i++) {
			int x1 = tailPos.get(i).x;
			int y1 = tailPos.get(i).y;
			for (int k = 0; k < tailPos.size(); k++) {
				if(k !=i){
					int x2 = tailPos.get(k).x;
					int y2 = tailPos.get(k).y;
					if (x1 ==x2 && y1 == y2){
						return true;
					}
				}
			}
		}

		//finish game when snake reach the border of panel
		if (this.getX() < 0) {
			return true;
		} else if (this.getX() >= (panel.getWidth() - this.getWidth())) {
			return true;
		} else if (this.getY() < 0) {
			return true;
		} else return this.getY() >= (panel.getHeight() - this.getHeight());
	}

	public void handleTouchInput(MotionEvent event) {
		if (getVelocity().getYSpeed() == 0) {
			if (event.getY() < this.getY()) {
				getVelocity().stop().setYDirection(Velocity.DIRECTION_UP).setYSpeed(STEP);
			} else if (event.getY() > this.getY() && getVelocity().getYSpeed() == 0) {
				getVelocity().stop().setYDirection(Velocity.DIRECTION_DOWN).setYSpeed(STEP);
			}
		} else if (getVelocity().getXSpeed() == 0) {
			if (event.getX() < this.getX()) {
				getVelocity().stop().setXDirection(Velocity.DIRECTION_LEFT).setXSpeed(STEP);
			} else if (event.getX() > this.getX()) {
				getVelocity().stop().setXDirection(Velocity.DIRECTION_RIGHT).setXSpeed(STEP);
			}
		}
	}
}
