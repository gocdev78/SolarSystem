import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CelestialBodyTest {
    @Test
    public void testCelestialBodyInitialization() {
        Vector3D position = new Vector3D(1, 2, 3);
        Vector3D velocity = new Vector3D(4, 5, 6);
        CelestialBody body = new CelestialBody("Earth", 5.972 * Math.pow(10, 24), 6371, position, velocity, "earth.jpg");

        assertEquals("Earth", body.getName());
        assertEquals(5.972 * Math.pow(10, 24), body.getMass());
        assertEquals(6371, body.getRadius());
        assertEquals(position, body.getPosition());
        assertEquals(velocity, body.getVelocity());
        assertEquals("earth.jpg", body.getTextureFilename());
    }

    // Możemy przetestować również metody setPosition, setVelocity, setRadius
    @Test
    public void testSetPosition() {
        Vector3D position = new Vector3D(1, 2, 3);
        Vector3D velocity = new Vector3D(4, 5, 6);
        CelestialBody body = new CelestialBody("Earth", 5.972 * Math.pow(10, 24), 6371, position, velocity, "earth.jpg");

        Vector3D newPosition = new Vector3D(7, 8, 9);
        body.setPosition(newPosition);

        assertEquals(newPosition, body.getPosition());
    }

    @Test
    public void testSetVelocity() {
        Vector3D position = new Vector3D(1, 2, 3);
        Vector3D velocity = new Vector3D(4, 5, 6);
        CelestialBody body = new CelestialBody("Earth", 5.972 * Math.pow(10, 24), 6371, position, velocity, "earth.jpg");

        Vector3D newVelocity = new Vector3D(7, 8, 9);
        body.setVelocity(newVelocity);

        assertEquals(newVelocity, body.getVelocity());
    }

    @Test
    public void testSetRadius() {
        Vector3D position = new Vector3D(1, 2, 3);
        Vector3D velocity = new Vector3D(4, 5, 6);
        CelestialBody body = new CelestialBody("Earth", 5.972 * Math.pow(10, 24), 6371, position, velocity, "earth.jpg");

        body.setRadius(7000);

        assertEquals(7000, body.getRadius());
    }
}
