package game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    @Test
    void testOf() {
        assertThrows(IllegalArgumentException.class, () -> Actor.of(-1));
        assertEquals(Actor.EMPTY, Actor.of(0));
        assertEquals(Actor.WALL, Actor.of(1));
        assertEquals(Actor.CHARACTER, Actor.of(2));
        assertEquals(Actor.BALL, Actor.of(3));
        assertEquals(Actor.STORAGE, Actor.of(4));
        assertThrows(IllegalArgumentException.class, () -> Actor.of(5));
    }

}
