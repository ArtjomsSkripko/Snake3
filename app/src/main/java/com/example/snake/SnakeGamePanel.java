package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


public class SnakeGamePanel extends AbstractGamePanel {

	public SnakeGamePanel(Context context) {
		super(context);
	}

	private SnakeActor snake;
	private DotActor apple;
	private ScoreBoard score;
	private boolean firstTouch = false;
	private static final long DOUBLE_CLICK_TIME_DELTA = 150;
	private long time;

	@Override
	public void onStart() {
		this.snake = new SnakeActor(100, 100);
		this.apple = new DotActor(200, 50);
		this.score = new ScoreBoard(this);
	}

	@Override
	public void onTimer() {
			if (this.snake.checkBoundsCollision(this)) {
				this.snake.setEnabled(false);
			}
			this.snake.move();
			if (this.apple.intersect(this.snake)) {
				this.snake.grow();
				this.score.earnPoints(50);
				this.apple.reposition(this);
			}
	}

	@Override
	public void redrawCanvas(Canvas canvas) {
		if (this.snake.isEnabled()) {
			this.snake.draw(canvas);
			this.apple.draw(canvas);
			this.score.draw(canvas);
		} else {
			Paint p = getPaint();
			p.setTextSize(100);
			p.setColor(Color.BLACK);
			canvas.drawText("Game over!", 100, 300, p);
			this.score.draw(canvas,100, 600, 100);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			if (firstTouch && (System.currentTimeMillis() - time) <= DOUBLE_CLICK_TIME_DELTA) {
				firstTouch = false;
				this.onStart();
				return true;
			} else {
				firstTouch = true;
				time = System.currentTimeMillis();
				this.snake.handleTouchInput(event);
				return true;
			}
		}
		return false;
	}

}
