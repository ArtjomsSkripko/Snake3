package com.example.snake.threads;

import android.view.SurfaceHolder;
import com.example.snake.AbstractGamePanel;

public abstract class BaseGameThread extends Thread {
	// Store references to the game panel and holder
	protected SurfaceHolder surfaceHolder;
	protected AbstractGamePanel gamePanel;

	// flag to hold game state
	protected boolean running;

	public void setRunning(boolean running) {
		this.running = running;
	}

	public BaseGameThread(SurfaceHolder surfaceHolder, AbstractGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	
	public abstract void run();
}
