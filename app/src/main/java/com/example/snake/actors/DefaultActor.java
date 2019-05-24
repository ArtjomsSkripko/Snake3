package com.example.snake.actors;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class DefaultActor {
	private Paint paint;
	private boolean enabled;
	
	public DefaultActor() {
		this.enabled = true;
		this.paint = new Paint();
		stylePaint(paint);
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean state) {
		this.enabled = state;
	}
	
	public abstract void stylePaint(Paint p);
	public abstract void draw(Canvas canvas);
}
