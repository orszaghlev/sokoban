package game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SokobanStateTest {

    private void assertCharacterSpace(int expectedCharacterRow, int expectedCharacterCol, SokobanState state) {
        assertAll(
                () -> assertEquals(expectedCharacterRow, state.getCharacterRow()),
                () -> assertEquals(expectedCharacterCol, state.getCharacterCol())
        );
    }

    @Test
    void testSokobanStateByteArrayArrayInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(null));
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(new int[][] {
                {1, 1},
                {1, 0}})
        );
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(new int[][] {
                {0},
                {1, 2},
                {3, 4, 5}})
        );
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}})
        );
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(new int[][] {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}})
        );
        assertThrows(IllegalArgumentException.class, () -> new SokobanState(new int[][] {
                {0, 1, 1, 3, 4, 2, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 2, 2, 5, 1, 2, 1},
                {3, 1, 2, 4, 3, 5, 2, 0, 4},
                {2, 2, 2, 2, 2, 2, 2, 2, 2},
                {0, 2, 0, 4, 0, 1, 0, 3, 1},
                {3, 3, 3, 3, 3, 3, 3, 3, 3},
                {1, 4, 1, 2, 1, 3, 1, 0, 1},
                {4, 4, 4, 4, 4, 4, 4, 4, 4}})
        );
    }

    @Test
    void testSokobanStateByteArrayArrayValidArgument() {
        int[][] a = new int[][] {
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
        SokobanState state = new SokobanState(a);
        assertArrayEquals(new Actor[][] {
                {Actor.WALL, Actor.WALL, Actor.WALL, Actor.WALL, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY},
                {Actor.WALL, Actor.CHARACTER, Actor.EMPTY, Actor.EMPTY, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY},
                {Actor.WALL, Actor.EMPTY, Actor.BALL, Actor.BALL, Actor.WALL, Actor.EMPTY, Actor.WALL, Actor.WALL, Actor.WALL},
                {Actor.WALL, Actor.EMPTY, Actor.BALL, Actor.EMPTY, Actor.WALL, Actor.EMPTY, Actor.WALL, Actor.STORAGE0, Actor.WALL},
                {Actor.WALL, Actor.WALL, Actor.WALL, Actor.EMPTY, Actor.WALL, Actor.WALL, Actor.WALL, Actor.STORAGE0, Actor.WALL},
                {Actor.EMPTY, Actor.WALL, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.STORAGE0, Actor.WALL},
                {Actor.EMPTY, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.WALL},
                {Actor.EMPTY, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY, Actor.WALL, Actor.WALL, Actor.WALL, Actor.WALL},
                {Actor.EMPTY, Actor.WALL, Actor.WALL, Actor.WALL, Actor.WALL, Actor.WALL, Actor.EMPTY, Actor.EMPTY, Actor.EMPTY}
        }, state.getTray());
        assertCharacterSpace(1, 1, state);
    }

    @Test
    void testIsSolved() {
        assertFalse(new SokobanState().isSolved());
        assertTrue(new SokobanState(new int[][] {
                {1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 5, 1},
                {1, 1, 1, 0, 1, 1, 1, 5, 1},
                {0, 1, 1, 0, 0, 0, 2, 5, 1},
                {0, 1, 0, 0, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0, 0, 0}}).isSolved());
    }

    @Test
    void testCanMoveToEmptySpace() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        assertFalse(state.canMoveToEmptySpace(1, 1));
        assertFalse(state.canMoveToEmptySpace(0, 2));
        assertTrue(state.canMoveToEmptySpace(1, 2));
        assertTrue(state.canMoveToEmptySpace(2, 1));
    }

    @Test
    void testGetMoveDirection() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        assertEquals(Direction.UP, state.getMoveDirection(2, 1));
        assertEquals(Direction.RIGHT, state.getMoveDirection(1, 0));
        assertEquals(Direction.DOWN, state.getMoveDirection(0, 1));
        assertEquals(Direction.LEFT, state.getMoveDirection(1, 2));
        assertThrows(IllegalArgumentException.class, () -> state.getMoveDirection(1, 1));
        assertThrows(IllegalArgumentException.class, () -> state.getMoveDirection(0, 0));
        assertThrows(IllegalArgumentException.class, () -> state.getMoveDirection(0, 2));
        assertThrows(IllegalArgumentException.class, () -> state.getMoveDirection(2, 0));
    }

    @Test
    void testMoveToEmptySpace() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        Actor actor = state.getTray()[2][1];
        state.moveToEmptySpace(2, 1);
        assertCharacterSpace(2, 1, state);
        assertEquals(actor.moveTo(Direction.DOWN), state.getTray()[1][1]);
        state.moveToEmptySpace(1, 1);
        assertCharacterSpace(1, 1, state);
        assertEquals(actor, state.getTray()[2][1]);
    }

    @Test
    void testCheckWallCollision() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        assertFalse(state.checkWallCollision(1, 2));
        assertFalse(state.checkWallCollision(2, 1));
        assertTrue(state.checkWallCollision(0, 1));
        assertTrue(state.checkWallCollision(1, 0));
    }

    @Test
    void testCheckBallCollision() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        state.moveToEmptySpace(2, 1);
        assertCharacterSpace(2, 1, state);
        assertFalse(state.checkBallCollision(1, 1));
        assertFalse(state.checkBallCollision(3, 1));
        assertTrue(state.checkBallCollision(2, 2));
    }

    @Test
    void testPushBall() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        state.moveToEmptySpace(1, 2);
        assertCharacterSpace(1, 2, state);
        state.moveToEmptySpace(1, 3);
        assertCharacterSpace(1, 3, state);
        state.pushBall(2, 3);
        assertCharacterSpace(2, 3, state);
        assertEquals(Actor.BALL, state.getTray()[3][3]);
    }

    @Test
    void testIsBallPlaced() {

    }

    @Test
    void testFillStorage() {
        SokobanState state = new SokobanState();
        assertCharacterSpace(1, 1, state);
        state.moveToEmptySpace(1, 2);
        assertCharacterSpace(1, 2, state);
        state.moveToEmptySpace(1, 3);
        assertCharacterSpace(1, 3, state);
        state.moveToEmptySpace(2, 3);
        assertCharacterSpace(2, 3, state);
        state.moveToEmptySpace(3, 3);
        assertCharacterSpace(3, 3, state);
        state.moveToEmptySpace(4, 3);
        assertCharacterSpace(4, 3, state);
        state.moveToEmptySpace(5, 3);
        assertCharacterSpace(5, 3, state);
        state.moveToEmptySpace(5, 4);
        assertCharacterSpace(5, 4, state);
        state.moveToEmptySpace(6, 4);
        assertCharacterSpace(6, 4, state);
        state.moveToEmptySpace(7, 4);
        assertCharacterSpace(7, 4, state);
        state.moveToEmptySpace(7, 3);
        assertCharacterSpace(7, 3, state);
        state.moveToEmptySpace(7, 2);
        assertCharacterSpace(7, 2, state);
        state.moveToEmptySpace(6, 2);
        assertCharacterSpace(6, 2, state);
        state.moveToEmptySpace(6, 3);
        assertCharacterSpace(6, 3, state);
        state.moveToEmptySpace(7, 3);
        assertCharacterSpace(7, 3, state);
        state.moveToEmptySpace(7, 4);
        assertCharacterSpace(7, 4, state);
        state.moveToEmptySpace(6, 4);
        assertCharacterSpace(6, 4, state);
        state.moveToEmptySpace(6, 3);
        assertCharacterSpace(6, 3, state);
        state.moveToEmptySpace(5, 3);
        assertCharacterSpace(5, 3, state);
        state.moveToEmptySpace(5, 4);
        assertCharacterSpace(5, 4, state);
        state.moveToEmptySpace(5, 5);
        assertCharacterSpace(5, 5, state);
        state.fillStorage(5, 6);
        assertCharacterSpace(5, 6, state);
        assertEquals(Actor.STORAGE1, state.getTray()[5][7]);
    }

    @Test
    void testToString() {
        SokobanState state = new SokobanState();
        assertEquals("1 1 1 1 1 0 0 0 0 \n"
                + "1 2 0 0 1 0 0 0 0 \n"
                + "1 0 3 3 1 0 1 1 1 \n"
                + "1 0 3 0 1 0 1 4 1 \n"
                + "1 1 1 0 1 1 1 4 1 \n"
                + "0 1 1 0 0 0 0 4 1 \n"
                + "0 1 0 0 0 1 0 0 1 \n"
                + "0 1 0 0 0 1 1 1 1 \n"
                + "0 1 1 1 1 1 0 0 0 \n", state.toString());
    }

}
