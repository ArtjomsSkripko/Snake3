package com.example.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.snake.threads.BaseGameThread;
import com.example.snake.threads.TimerGameThread;

public abstract class AbstractGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private BaseGameThread gameThread;
	private Paint paint;
	private int panelColor;
	
	public AbstractGamePanel(Context context) {
		this(TimerGameThread.class, context);
	}

	public AbstractGamePanel(Class<? extends BaseGameThread> loopClass, Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		// create the game loop thread
		setupGameLoop(loopClass);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();
		setWillNotDraw(true);
		// setup brush
		panelColor = Color.WHITE;
		paint = new Paint();
	}

	// Draw the game board
	public void render(Canvas canvas) {
		// clear canvas
		canvas.drawColor(panelColor);
		// draw canvas
		this.redrawCanvas(canvas);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// start game thread
		startGameLoop();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	// Game launch
	public abstract void onStart();
	// Called every "tick" to apply game logic
	public abstract void onTimer();
	// Called every second to redrawCanvas
	public abstract void redrawCanvas(Canvas canvas);
	// Handle touch inputs in the game
	public abstract boolean onTouchEvent(MotionEvent event);
	
	// Returns the primary paint object for drawing styles
	protected Paint getPaint() {
		return paint;
	}
	
	// Starts the game loop
	protected void startGameLoop() {
		gameThread.setRunning(true);
		gameThread.start();
	}
	
	// Create game loop given loop type
	protected void setupGameLoop(Class<? extends BaseGameThread> loopClass) {
		try {
			gameThread = (BaseGameThread) loopClass.getConstructors()[0].newInstance(getHolder(), this);
		} catch (Exception e) {
			// Do nothing
		}
	}
}