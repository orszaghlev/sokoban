package game.state;

import javafx.scene.image.Image;

/**
 * The Actor class creates the functionality of the actors in the game:
 * the ball, the storage, the wall and the character.
 * @author orszaghlev
 */

public class Actor {

    public static final int SPACE = 64;

    public static int x;
    public static int y;
    public static Image image;

    /**
     * The constructor of an actor in the game.
     * @param x the x coordinate of the actor
     * @param y the y coordinate of the actor
     */

    public Actor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the image of the actor
     */

    public Image getImage() {
        return image;
    }

    /**
     *
     * @param img the image to set
     */

    public void setImage(Image img) {
        image = img;
    }

    /**
     *
     * @return the x coordinate of the actor
     */

    public static int x() {
        return x;
    }

    /**
     *
     * @return the y coordinate of the actor
     */

    public static int y() {
        return y;
    }

    /**
     *
     * @param x the x coordinate to set
     */

    public void setX(int x){
        this.x = x;
    }

    /**
     *
     * @param y the y coordinate to set
     */

    public void setY(int y){
        this.y = y;
    }

    /**
     * Decides if the <code>actor</code> collides with another actor to the left.
     * @param actor the current actor
     * @return true or false
     */

    public static boolean isLeftCollision(Actor actor) {
        return x() - SPACE == actor.x() && y() == actor.y();
    }

    /**
     * Decides if the <code>actor</code> collides with another actor to the right.
     * @param actor the current actor
     * @return true or false
     */

    public static boolean isRightCollision(Actor actor) {
        return x() + SPACE == actor.x() && y() == actor.y();
    }

    /**
     * Decides if the <code>actor</code> collides with another actor to the top.
     * @param actor the current actor
     * @return true or false
     */

    public static boolean isTopCollision(Actor actor) {
        return y() - SPACE == actor.y() && x() == actor.x();
    }

    /**
     * Decides if the <code>actor</code> collides with another actor to the bottom.
     * @param actor the current actor
     * @return true or false
     */

    public static boolean isBottomCollision(Actor actor) {
        return y() + SPACE == actor.y() && x() == actor.x();
    }

}
