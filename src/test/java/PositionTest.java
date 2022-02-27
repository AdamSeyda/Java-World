package test.java;

import main.java.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {
    @Test
    public void testDifferentInstancesSameValues(){
        Position p = new Position(1, 0);
        assertEquals(p,new Position(1, 0));
    }

    @Test
    public void testSameInstance(){
        Position p = new Position(1, 0);
        assertEquals(p,p);
    }

}
