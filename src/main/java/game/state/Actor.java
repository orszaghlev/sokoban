package game.state;

/**
 * The Actor enum represents the actors of the game:
 * the ball, the storage, the wall and the character.
 * @author orszaghlev
 */
public enum Actor {

    EMPTY,
    WALL,
    CHARACTER,
    BALL,
    STORAGE0,
    STORAGE1;

    private static final int[][] T = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    /**
     * Returns the instance represented by the value specified.
     *
     * @param value the value representing an instance
     * @return the instance represented by the value specified
     * @throws IllegalArgumentException if the value specified does not
     * represent an instance
     */
    public static Actor of(int value) {
        if (value < 0 || value >= values().length) {
            throw new IllegalArgumentException();
        }
        return values()[value];
    }

    /**
     * Returns the integer value that represents this instance.
     *
     * @return the integer value that represents this instance
     */
    public int getValue() {
        return ordinal();
    }

    /**
     * Moves the actor to the direction specified.
     *
     * @param direction the direction to which the actor is moved
     * @return the actor moved to the direction specified
     * @throws UnsupportedOperationException if the method is invoked on the
     * {@link #CHARACTER} instance.
     */
    public Actor moveTo(Direction direction) {
        if (this == CHARACTER) {
            throw new UnsupportedOperationException();
        }
        return values()[T[ordinal()][direction.ordinal()]];
    }

    public String toString() {
        return Integer.toString(ordinal());
    }

}
