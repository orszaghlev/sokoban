package game.state;

import javafx.scene.image.Image;

/**
 * The Storage class inherits from the Actor class
 * as the storage is an actor in the game.
 * @author orszaghlev
 */

public class Storage extends Actor {

    public static Image storageImage;

    /**
     * The constructor of the storage, calls the function
     * that initializes the actor.
     * @param x the x coordinate of the storage
     * @param y the y coordinate of the storage
     */

    public Storage(int x, int y) {
        super(x,y);

        initStorage();
    }

    /**
     * Loads the image of the storage.
     */

    public void initStorage() {

        storageImage = new Image(getClass().getResource("/pictures/storage.png").toExternalForm());

    }

}