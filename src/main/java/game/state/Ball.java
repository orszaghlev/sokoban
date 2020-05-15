package game.state;

import javafx.scene.image.Image;

/**
 * The Ball class inherits from the Actor class
 * as the ball is an actor in the game.
 * @author orszaghlev
 */

public class Ball extends Actor {

    public static Image ballImage;

    /**
     * The constructor of the ball, calls the function
     * that initializes the actor.
     * @param x the x coordinate of the ball
     * @param y the y coordinate of the ball
     */

    public Ball(int x, int y) {
        super(x,y);

        initBall();
    }

    /**
     * Loads the image of the ball.
     */

    public void initBall() {

        ballImage = new Image(getClass().getResource("/pictures/ball.png").toExternalForm());

    }

    /**
     * Moves the ball through the level by getting the
     * <code>x</code> and <code>y</code> coordinates
     * (the current position) of the actor and increasing it with
     * the coordinate values of the new position.
     * @param x the current x coordinate of the ball
     * @param y the current y coordinate of the ball
     */

    public void move(int x, int y) {
        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }

}