package game.state;

import javafx.scene.image.Image;

/**
 * The Character class inherits from the Actor class
 * as the character is an actor in the game.
 * @author orszaghlev
 */

public class Character extends Actor {

    public static Image characterImage;

    /**
     * The constructor of the character, calls the function
     * that initializes the actor.
     * @param x the x coordinate of the character
     * @param y the y coordinate of the character
     */

    public Character(int x, int y) {
        super(x,y);

        initCharacter();
    }

    /**
     * Loads the image of the character.
     */

    public void initCharacter() {

        characterImage = new Image(getClass().getResource("/pictures/character.png").toExternalForm());

    }

    /**
     * Moves the character through the level by getting the
     * <code>x</code> and <code>y</code> coordinates
     * (the current position) of the actor and increasing it with
     * the coordinate values of the new position.
     * @param x the current x coordinate of the character
     * @param y the current y coordinate of the character
     */

    public void move(int x, int y) {
        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }

}