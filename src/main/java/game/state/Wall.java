package game.state;

import javafx.scene.image.Image;

/**
 * The Wall class inherits from the Actor class
 * as the wall is an actor in the game.
 * @author orszaghlev
 */

public class Wall extends Actor {

    public static Image wallImage;

    /**
     * The constructor of the wall, calls the function
     * that initializes the actor.
     * @param x the x coordinate of the wall
     * @param y the y coordinate of the wall
     */

    public Wall(int x, int y) {
        super(x,y);

        initWall();
    }

    /**
     * Loads the image of the wall.
     */

    public void initWall() {

        wallImage = new Image(getClass().getResource("/pictures/wall.png").toExternalForm());

    }

}
