package game.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class representing the state of the game.
 */
@Data
@Slf4j
public class SokobanState implements Cloneable {

    /**
     * The array representing the initial configuration of the tray.
     */
    public static final int[][] INITIAL = {
            {1, 1, 1, 1, 1, 0, 0, 0, 0},
            {1, 2, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 3, 3, 1, 0, 1, 1, 1},
            {1, 0, 3, 0, 1, 0, 1, 4, 1},
            {1, 1, 1, 0, 1, 1, 1, 4, 1},
            {0, 1, 1, 0, 0, 0, 0, 4, 1},
            {0, 1, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 0, 0, 0}
    };

    /**
     * The array representing the goal configuration of the tray.
     */
    public static final int[][] GOAL = {
            {1, 1, 1, 1, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 1, 5, 1},
            {1, 1, 1, 0, 1, 1, 1, 5, 1},
            {0, 1, 1, 0, 0, 0, 2, 5, 1},
            {0, 1, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 0, 0, 0}
    };

    /**
     * The array storing the current configuration of the tray.
     */
    @Setter(AccessLevel.NONE)
    private Actor[][] tray;

    /**
     * The row of the character's position.
     */
    @Setter(AccessLevel.NONE)
    private int characterRow;

    /**
     * The column of the character's position.
     */
    @Setter(AccessLevel.NONE)
    private int characterCol;

    /**
     * The constructor of the class, initializes the level.
     */
    public SokobanState() {
        this(INITIAL);
    }

    /**
     * Creates a {@code SokobanState} object that is initialized with
     * the specified array.
     *
     * @param a an array of size 3&#xd7;3 representing the initial configuration
     *          of the tray
     */
    public SokobanState(int[][] a) {
        if (!isValidLevel(a)) {
            throw new IllegalArgumentException();
        }
        initLevel(a);
    }

    private boolean isValidLevel(int[][] a) {
        if (a == null || a.length != 9) {
            return false;
        }
        boolean foundCharacter = false;
        for (int[] row : a) {
            if (row == null || row.length != 9) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Actor.values().length) {
                    return false;
                }
                if (space == Actor.CHARACTER.getValue()) {
                    if (foundCharacter) {
                        return false;
                    }
                    foundCharacter = true;
                }
            }
        }
        return foundCharacter;
    }

    private void initLevel(int[][] a) {
        this.tray = new Actor[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if ((this.tray[i][j] = Actor.of(a[i][j])) == Actor.CHARACTER) {
                    characterRow = i;
                    characterCol = j;
                }
            }
        }
    }

    /**
     * Checks whether the level is completed.
     *
     * @return {@code true} if the level is completed, {@code false} otherwise
     */
    public boolean isSolved() {
        if (tray[3][7] == Actor.STORAGE1 && tray[4][7] == Actor.STORAGE1 && tray[5][7] == Actor.STORAGE1) {
            return true;
        }
        return false;
    }

    /**
     * Returns whether the character can be moved.
     *
     * @param row the row where the character would be moved
     * @param col the column where the character would be moved
     * @return {@code true} if the character can be moved,
     * {@code false} otherwise
     */
    public boolean canMove(int row, int col) {
        if (tray[row][col] == Actor.EMPTY || tray[row][col] == Actor.BALL || tray[row][col] == Actor.STORAGE0
            || tray[row][col] == Actor.STORAGE1) {
            return true;
        }
        return false;
    }

    /**
     * Returns the direction to which the character is
     * moved.
     *
     * @param row the row where the character would be moved
     * @param col the column where the character would be moved
     * @return the direction to which the character is
     * moved
     * @throws IllegalArgumentException if the character
     * can not be moved to the empty space
     */
    public Direction getMoveDirection(int row, int col) {
        //if (!canMove(row, col)) {
            //throw new IllegalArgumentException();
        //}
        return Direction.of(characterRow - row, characterCol - col);
    }

    /**
     * Moves the character to the empty space.
     *
     * @param row the row where the character would be moved
     * @param col the column where the character would be moved
     */
    public void moveToEmptySpace(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        log.info("Player moved to ({},{}) from {}", row, col, direction);
        tray[characterRow][characterCol] = tray[row][col].moveTo(direction);
        tray[row][col] = Actor.CHARACTER;
        characterRow = row;
        characterCol = col;
    }

    /**
     * Returns whether the character could collide
     * with the wall when moving to the specified position.
     *
     * @param row the row where the character would be moved to
     * @param col the column where the character would be moved to
     * @return {@code true} if the character could collide
     * with the wall, {@code false} otherwise
     */
    public boolean checkWallCollision(int row, int col) {
        if (tray[row][col] == Actor.WALL) {
            return true;
        }
        return false;
    }

    /**
     * Returns whether the character could collide
     * with a ball when moving to the specified position.
     *
     * @param row the row where the character would be moved to
     * @param col the column where the character would be moved to
     * @return {@code true} if the character could collide
     * with a ball, {@code false} otherwise
     */
    public boolean checkBallCollision(int row, int col) {
        if (tray[row][col] == Actor.BALL || tray[row][col] == Actor.STORAGE1) {
            return true;
        }
        return false;
    }

    /**
     * Returns whether the character would make the
     * ball disappear when moving to the specified position.
     *
     * @param row the row where the character would be moved to
     * @param col the column where the character would be moved to
     * @return {@code true} if the character would make the
     * ball disappear, {@code false} otherwise
     */
    public boolean checksBallDisappearance(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        if (direction == Direction.UP && (tray[row+1][col] == Actor.WALL || tray[row+1][col] == Actor.BALL)) {
            return true;
        }
        if (direction == Direction.DOWN && (tray[row-1][col] == Actor.WALL || tray[row-1][col] == Actor.BALL)) {
            return true;
        }
        if (direction == Direction.LEFT && (tray[row][col+1] == Actor.WALL || tray[row][col+1] == Actor.BALL)) {
            return true;
        }
        if (direction == Direction.RIGHT && (tray[row][col-1] == Actor.WALL || tray[row][col-1] == Actor.BALL)) {
            return true;
        }
        return false;
    }

    /**
     * Avoids ball disappearance by checking which actor is
     * at the position where the ball would be pushed.
     *
     * @param row the row where the character would be moved to
     * @param col the column where the character would be moved to
     */
    public void avoidBallDisappearance(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        if (direction == Direction.UP) {
                tray[row][col] = Actor.BALL;
                tray[row-1][col] = Actor.CHARACTER;
        }
        if (direction == Direction.DOWN) {
                tray[row][col] = Actor.BALL;
                tray[row+1][col] = Actor.CHARACTER;
        }
        if (direction == Direction.LEFT) {
                tray[row][col] = Actor.BALL;
                tray[row][col-1] = Actor.CHARACTER;
        }
        if (direction == Direction.RIGHT) {
                tray[row][col] = Actor.BALL;
                tray[row][col+1] = Actor.CHARACTER;
        }
    }

    /**
     * Moves the character to the ball's original space
     * and pushes the ball further.
     *
     * @param row the row where the character would be moved to
     *            (the ball's original row)
     * @param col the column where the character would be moved to
     *            (the ball's original column)
     */
    public void pushBall(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        moveToEmptySpace(row, col);
        log.info("Player pushed a ball");
        if (direction == Direction.UP) {
            if (!checkWallCollision(row+1, col)) {
                tray[row+1][col] = Actor.BALL;
                tray[row-1][col] = Actor.EMPTY;
            }
        }
        if (direction == Direction.DOWN) {
            if (!checkWallCollision(row-1, col)) {
                tray[row-1][col] = Actor.BALL;
                tray[row+1][col] = Actor.EMPTY;
            }
        }
        if (direction == Direction.LEFT) {
            if (!checkWallCollision(row, col+1)) {
                tray[row][col+1] = Actor.BALL;
                tray[row][col-1] = Actor.EMPTY;
            }
        }
        if (direction == Direction.RIGHT) {
            if (!checkWallCollision(row, col-1)) {
                tray[row][col-1] = Actor.BALL;
                tray[row][col+1] = Actor.EMPTY;
            }
        }
    }

    /**
     * Returns whether the ball was placed in
     * one of the storages.
     *
     * @param row the row where the character would be moved to
     * @param col the column where the character would be moved to
     * @return {@code true} if the ball was placed in
     * one of the storages, {@code false} otherwise
     */
    public boolean isBallPlaced(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        if (direction == Direction.UP && tray[characterRow+2][characterCol] == Actor.STORAGE0) {
            return true;
        }
        if (direction == Direction.DOWN && tray[characterRow-2][characterCol] == Actor.STORAGE0) {
            return true;
        }
        if (direction == Direction.LEFT && tray[characterRow][characterCol+2] == Actor.STORAGE0) {
            return true;
        }
        if (direction == Direction.RIGHT && tray[characterRow][characterCol-2] == Actor.STORAGE0) {
            return true;
        }
        return false;
    }

    /**
     * Moves the character to the ball's original space
     * and fills one of the storages.
     *
     * @param row the row where the character would be moved to
     *            (the ball's original row)
     * @param col the column where the character would be moved to
     *            (the ball's original column)
     */
    public void fillStorage(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        moveToEmptySpace(row, col);
        log.info("Player filled a storage");
        if (direction == Direction.UP) {
            if (!checkWallCollision(row+1, col)) {
                tray[row+1][col] = Actor.STORAGE1;
            }
        }
        if (direction == Direction.DOWN) {
            if (!checkWallCollision(row-1, col)) {
                tray[row-1][col] = Actor.STORAGE1;
            }
        }
        if (direction == Direction.LEFT) {
            if (!checkWallCollision(row, col+1)) {
                tray[row][col+1] = Actor.STORAGE1;
            }
        }
        if (direction == Direction.RIGHT) {
            if (!checkWallCollision(row, col-1)) {
                tray[row][col-1] = Actor.STORAGE1;
            }
        }
    }

    /**
     * Checks if the empty storages are on the level
     * and places them back if the player was standing
     * in their original position.
     */
    public void placeEmptyStorage() {
        if (!(tray[3][7] == Actor.STORAGE1 || tray[3][7] == Actor.CHARACTER)) {
            tray[3][7] = Actor.STORAGE0;
        }
        if (!(tray[4][7] == Actor.STORAGE1 || tray[4][7] == Actor.CHARACTER)) {
            tray[4][7] = Actor.STORAGE0;
        }
        if (!(tray[5][7] == Actor.STORAGE1 || tray[5][7] == Actor.CHARACTER)) {
            tray[5][7] = Actor.STORAGE0;
        }
    }

    public SokobanState clone() {
        SokobanState copy = null;
        try {
            copy = (SokobanState) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        copy.tray = new Actor[tray.length][];
        for (int i = 0; i < tray.length; ++i) {
            copy.tray[i] = tray[i].clone();
        }
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Actor[] row : tray) {
            for (Actor actor : row) {
                sb.append(actor).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SokobanState state = new SokobanState();
        System.out.println(state);
        state.moveToEmptySpace(1, 1);
        System.out.println(state);
    }
}