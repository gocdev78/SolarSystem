import java.util.ArrayList;
import java.util.List;

public class SolarSystem {
    private List<DisplayedCelestialBody> bodies;

    public SolarSystem() {
        bodies = new ArrayList<>();
    }

    public void addBody(DisplayedCelestialBody body) {
        bodies.add(body);
    }

    public List<DisplayedCelestialBody> getBodies() {
        return bodies;
    }
}