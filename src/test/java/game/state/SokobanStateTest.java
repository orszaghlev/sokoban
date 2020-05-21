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
    void testIsSolved() {
        assertFalse(new SokobanState().isSolved());
        assertTrue(new SokobanState(new int[][] {
                {1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 1, 3, 1},
                {1, 1, 1, 0, 1, 1, 1, 3, 1},
                {0, 1, 1, 0, 0, 0, 2, 3, 1},
                {0, 1, 0, 0, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 0, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0, 0, 0}}).isSolved());
    }

    @Test
    void testToString() {
        SokobanState state = new SokobanState();
        assertEquals("1 1 1 1 1 0 0 0 0\n"
                + "1 2 0 0 1 0 0 0 0\n"
                + "1 0 3 3 1 0 1 1 1\n"
                + "1 0 3 0 1 0 1 4 1\n"
                + "1 1 1 0 1 1 1 4 1\n"
                + "0 1 1 0 0 0 0 4 1\n"
                + "0 1 0 0 0 1 0 0 1\n"
                + "0 1 0 0 0 1 1 1 1\n"
                + "0 1 1 1 1 1 0 0 0\n", state.toString());
    }

}
