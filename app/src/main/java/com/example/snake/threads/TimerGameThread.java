package com.example.snake.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.snake.AbstractGamePanel;

public class TimerGameThread extends BaseGameThread {

    private int tickInterval;

	public TimerGameThread(SurfaceHolder surfaceHolder, AbstractGamePanel gamePanel) {
		super(surfaceHolder, gamePanel);
		tickInterval = 100;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		this.gamePanel.onStart();
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					// update game state
					this.gamePanel.onTimer();
					// render state to the screen
					this.gamePanel.render(canvas);
				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			
			if (tickInterval > 0) {
				try {
					Thread.sleep(tickInterval);	
				} catch (InterruptedException ignored) { }
			}
		}
	}
}
