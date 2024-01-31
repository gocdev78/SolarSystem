import java.util.ArrayList;
import java.util.List;

public class CelestialBody {
    private String name;
    private double mass;
    private double radius;
    private Vector3D position;
    private Vector3D velocity;
    private String textureFilename;
    private double rotationAngle; // Nowe pole
    private double orbitalSpeed; // Nowe pole
    private boolean isFixed; // Nowe pole
    private List<Vector3D> orbitPath; // Nowe pole

    public CelestialBody(String name, double mass, double radius, Vector3D position, Vector3D velocity, String textureFilename, double orbitalSpeed, boolean isFixed) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        this.textureFilename = textureFilename;
        this.rotationAngle = 0.0; // Inicjalizacja kąta obrotu
        this.orbitalSpeed = orbitalSpeed; // Inicjalizacja prędkości orbitalnej
        this.isFixed = isFixed; // Inicjalizacja wartości dla isFixed
    }

    public CelestialBody(String name, double mass, double radius, Vector3D position, Vector3D velocity, String textureFilename) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        this.textureFilename = textureFilename;
        this.rotationAngle = 0.0; // Inicjalizacja kąta obrotu
        this.orbitPath = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getVelocity() {
        return velocity;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public String getTextureFilename() {
        return textureFilename;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public void updateRotation(double deltaTime) {
        // Aktualizacja kąta obrotu
        this.rotationAngle += 360 * deltaTime / (5 * 60 / 100); // Zakładamy, że pełny obrót wokół własnej osi to 5 minut podzielone przez 365
        // Ograniczamy kąt obrotu do zakresu 0-360 stopni
        this.rotationAngle = this.rotationAngle % 360;
    }
    public double getOrbitalSpeed() {
        return this.orbitalSpeed;
    }

    public void setOrbitalSpeed(double orbitalSpeed) {
        this.orbitalSpeed = orbitalSpeed;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public boolean isFixed() {
        return isFixed;
    }
}