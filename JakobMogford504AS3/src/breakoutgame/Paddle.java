package breakoutgame;

import java.awt.Graphics;

public class Paddle extends Sprite {

	private int xVelocity;

	public Paddle() {
		width = Settings.PADDLE_WIDTH;
		height = Settings.PADDLE_HEIGHT;
		resetPosition();
	}

	public void resetPosition() {
		x = (Settings.INITIAL_PADDLE_X - Settings.PADDLE_WIDTH / 2);
		y = Settings.INITIAL_PADDLE_Y;
	}

	public void update() {
		x += xVelocity;
		
		// Prevent the paddle from moving outside of the screen
		if (x < 0) {
			x = 0;
		} else if (x + width > Settings.SCREEN_WIDTH) {
			x = Settings.SCREEN_WIDTH - width;
		}
	}
	
	public Paddle(int x, int y, int width, int height) {
	    this.x = x;
	    this.y = y;
	    this.width = width;
	    this.height = height;
	    xVelocity = 0;
	}

	public void paint(Graphics g) {
	    g.fillRect(x, y, width, height);
	}

	public void setXVelocity(int vel) {
		xVelocity = vel;
	}
}
