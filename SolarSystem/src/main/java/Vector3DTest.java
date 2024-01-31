import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector3DTest {
    @Test
    public void testAdd() {
        Vector3D vec1 = new Vector3D(1, 2, 3);
        Vector3D vec2 = new Vector3D(4, 5, 6);
        Vector3D result = vec1.add(vec2);

        assertEquals(5, result.getX());
        assertEquals(7, result.getY());
        assertEquals(9, result.getZ());
    }

    // Analogicznie możemy przetestować pozostałe metody: subtract, scale, magnitude
    @Test
    public void testSubtract() {
        Vector3D vec1 = new Vector3D(1, 2, 3);
        Vector3D vec2 = new Vector3D(4, 5, 6);
        Vector3D result = vec1.subtract(vec2);

        assertEquals(-3, result.getX());
        assertEquals(-3, result.getY());
        assertEquals(-3, result.getZ());
    }

    @Test
    public void testScale() {
        Vector3D vec1 = new Vector3D(1, 2, 3);
        Vector3D result = vec1.scale(3);

        assertEquals(3, result.getX());
        assertEquals(6, result.getY());
        assertEquals(9, result.getZ());
    }

    @Test
    public void testMagnitude() {
        Vector3D vec1 = new Vector3D(1, 2, 3);
        double result = vec1.magnitude();

        assertEquals(Math.sqrt(14), result);
    }
}
