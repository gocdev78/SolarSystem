import javafx.scene.shape.Sphere;
import javafx.scene.DepthTest;
public class DisplayedCelestialBody {
    private CelestialBody celestialBody;
    private Sphere displayObject;

    public DisplayedCelestialBody(CelestialBody celestialBody, Sphere displayObject) {
        this.celestialBody = celestialBody;
        this.displayObject = displayObject;
        this.displayObject.setDepthTest(DepthTest.ENABLE);
    }

    public CelestialBody getCelestialBody() {
        return celestialBody;
    }

    public Sphere getDisplayObject() {
        return displayObject;
    }
}