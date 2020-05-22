package game.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * The SokobanState class represents the state of the game.
 * @author orszaghlev
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
            {1, 0, 0, 0, 1, 0, 1, 3, 1},
            {1, 1, 1, 0, 1, 1, 1, 3, 1},
            {0, 1, 1, 0, 0, 0, 2, 3, 1},
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
     * Checks whether the puzzle is solved.
     *
     * @return {@code true} if the puzzle is solved, {@code false} otherwise
     */
    public boolean isSolved() {
        for (Actor[] row : tray) {
            for (Actor actor : row) {
                if (actor != Actor.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns whether the character at the specified position can be moved to the
     * empty space.
     *
     * @param row the row of the character to be moved
     * @param col the column of the character to be moved
     * @return {@code true} if the character at the specified position can be moved
     * to the empty space, {@code false} otherwise
     */
    public boolean canMoveToEmptySpace(int row, int col) {
        return 0 <= row && row <= 8 && 0 <= col && col <= 8 &&
                Math.abs(characterRow - row) + Math.abs(characterCol - col) == 1;
    }

    /**
     * Returns the direction to which the character at the specified position is
     * moved to the empty space.
     *
     * @param row the row of the character to be moved
     * @param col the column of the character to be moved
     * @return the direction to which the character at the specified position is
     * moved to the empty space
     * @throws IllegalArgumentException if the character at the specified position
     * can not be moved to the empty space
     */
    public Direction getMoveDirection(int row, int col) {
        if (!canMoveToEmptySpace(row, col)) {
            throw new IllegalArgumentException();
        }
        return Direction.of(characterRow - row, characterCol - col);
    }

    /**
     * Moves the player at the specified position to the empty space.
     *
     * @param row the row of the player to be moved
     * @param col the column of the player to be moved
     * @throws IllegalArgumentException if the character at the specified position
     * can not be moved to the empty space
     */
    public void moveToEmptySpace(int row, int col) {
        Direction direction = getMoveDirection(row, col);
        log.info("Player at ({},{}) is moved to {}", row, col, direction);
        tray[characterRow][characterCol] = tray[row][col].moveTo(direction);
        tray[row][col] = Actor.CHARACTER;
        characterRow = row;
        characterCol = col;
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