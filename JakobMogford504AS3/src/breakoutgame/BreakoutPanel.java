package breakoutgame;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BreakoutPanel extends JPanel implements ActionListener, KeyListener {

    static final long serialVersionUID = 2L;

    private boolean gameRunning = true;
    private int livesLeft = 3;
    private String screenMessage = "";
    private Ball ball;
    private Paddle paddle;
    private Brick bricks[];

    public BreakoutPanel(Breakout game) {
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow(); // Request focus for the panel

        Timer timer = new Timer(5, this);
        timer.start();

        // Create a new ball object and assign it to the appropriate variable
        ball = new Ball(Settings.WINDOW_WIDTH / 2 - Settings.BALL_WIDTH / 2, Settings.WINDOW_HEIGHT / 2 - Settings.BALL_HEIGHT / 2);
        // Create a new paddle object and assign it to the appropriate variable
        paddle = new Paddle(Settings.WINDOW_WIDTH / 2 - Settings.PADDLE_WIDTH / 2, Settings.WINDOW_HEIGHT - Settings.PADDLE_BOTTOM_OFFSET, Settings.PADDLE_WIDTH, Settings.PADDLE_HEIGHT);
        // Create a new bricks array (Use Settings.TOTAL_BRICKS)
        bricks = new Brick[Settings.TOTAL_BRICKS];
        // Call the createBricks() method
        createBricks();
    }

    private void createBricks() {
        int counter = 0;
        int x_space = 0;
        int y_space = 0;
        for(int x = 0; x < 4; x++) {
            for(int y = 0; y < 5; y++) {
                bricks[counter] = new Brick((x * Settings.BRICK_WIDTH) + Settings.BRICK_HORI_PADDING + x_space, (y * Settings.BRICK_HEIGHT) + Settings.BRICK_VERT_PADDING + y_space);
                counter++;
                y_space++;
            }
            x_space++;
            y_space = 0;
        }
    }

    private void paintBricks(Graphics g) {
        // Loop through the bricks and call the paint() method
        for (Brick brick : bricks) {
            if (!brick.isBroken()) {
                brick.paint(g);
            }
        }
    }

    private void update() {
        if(gameRunning) {
            // Update the ball and paddle
            ball.update();
            paddle.update();
            collisions();
            repaint();
        }
    }

    private void gameOver() {
        // Set screen message
        screenMessage = "Game Over";
        stopGame();
    }

    private void gameWon() {
        // Set screen message
        screenMessage = "You Won!";
        stopGame();
    }

    private void stopGame() {
        gameRunning = false;
    }

    private void collisions() {
        // Check for loss
        if(ball.y > 450) {
            // Game over
            livesLeft--;
            if(livesLeft <= 0) {
                gameOver();
                return;
            } else {
                ball.resetPosition();
                ball.setYVelocity(-1);
            }
        }

        // Check for win
        boolean bricksLeft = false;
        for(int i = 0; i < bricks.length; i++) {
            // Check if there are any bricks left
            if(!bricks[i].isBroken()) {
                // Brick was found, close loop
                bricksLeft = true;
                break;
            }
        }
		if(!bricksLeft) {
			gameWon();
			return;
		}
		
		// Check collisions
	    if(ball.getRectangle().intersects(paddle.getRectangle())) {
	        // Simplified touching of paddle
	        // Proper game would change angle of ball depending on where it hit the paddle
	        ball.setYVelocity(-1);
	    }

	    for(int i = 0; i < bricks.length; i++) {
	        if (ball.getRectangle().intersects(bricks[i].getRectangle())) {
	            int ballLeft = (int) ball.getRectangle().getMinX();
	            int ballHeight = (int) ball.getRectangle().getHeight();
	            int ballWidth = (int) ball.getRectangle().getWidth();
	            int ballTop = (int) ball.getRectangle().getMinY();

	            Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
	            Point pointLeft = new Point(ballLeft - 1, ballTop);
	            Point pointTop = new Point(ballLeft, ballTop - 1);
	            Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

	            if (!bricks[i].isBroken()) {
	                if (bricks[i].getRectangle().contains(pointRight)) {
	                    ball.setXVelocity(-1);
	                } else if (bricks[i].getRectangle().contains(pointLeft)) {
	                    ball.setXVelocity(1);
	                }

	                if (bricks[i].getRectangle().contains(pointTop)) {
	                    ball.setYVelocity(1);
	                } else if (bricks[i].getRectangle().contains(pointBottom)) {
	                    ball.setYVelocity(-1);
	                }
	                bricks[i].setBroken(true);
	            }
	        }
	    }
	}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        ball.paint(g);
        paddle.paint(g);
        paintBricks(g);

        // Draw lives left
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Lives left: " + livesLeft, 10, 20);

        // Draw screen message
        if (screenMessage != null) {
            g.setFont(new Font("Arial", Font.BOLD, 18));
            int messageWidth = g.getFontMetrics().stringWidth(screenMessage);
            g.drawString(screenMessage, (Settings.WINDOW_WIDTH / 2) - (messageWidth / 2), Settings.MESSAGE_POSITION);
        }
    }


	@Override
	public void keyPressed(KeyEvent e) {
	    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
	        paddle.setXVelocity(-1);
	    } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
	        paddle.setXVelocity(1);
	    }
	}

	@Override
	public void keyReleased(KeyEvent e) {
	    if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
	        paddle.setXVelocity(0);
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    update();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	    // Do nothing
	}


}
