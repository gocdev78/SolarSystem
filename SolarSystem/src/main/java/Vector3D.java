public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double distance(Vector3D other) {
        double dx = other.getX() - this.getX();
        double dy = other.getY() - this.getY();
        double dz = other.getZ() - this.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Vector3D add(Vector3D other) {
        return new Vector3D(
                this.getX() + other.getX(),
                this.getY() + other.getY(),
                this.getZ() + other.getZ()
        );
    }

    public Vector3D subtract(Vector3D other) {
        return new Vector3D(
                this.getX() - other.getX(),
                this.getY() - other.getY(),
                this.getZ() - other.getZ()
        );
    }

    public Vector3D multiply(double scalar) {
        return new Vector3D(
                this.getX() * scalar,
                this.getY() * scalar,
                this.getZ() * scalar
        );
    }

    public Vector3D normalize() {
        double magnitude = Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
        return new Vector3D(
                this.getX() / magnitude,
                this.getY() / magnitude,
                this.getZ() / magnitude
        );
    }

    public Vector3D crossProduct(Vector3D other) {
        return new Vector3D(
                this.getY() * other.getZ() - this.getZ() * other.getY(),
                this.getZ() * other.getX() - this.getX() * other.getZ(),
                this.getX() * other.getY() - this.getY() * other.getX()
        );
    }

    public double dotProduct(Vector3D other) {
        return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
    }

    // Dodaj tu pozostałe metody, które są w klasie Point3D, ale nie zostały tu pokazane.

    // Mnoży wektor przez skalar
    public Vector3D scale(double factor) {
        return new Vector3D(this.x * factor, this.y * factor, this.z * factor);
    }

    // Oblicza długość (magnitude) wektora
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
}