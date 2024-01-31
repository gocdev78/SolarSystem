import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import java.io.InputStream;
import javafx.scene.image.Image;
import java.util.List;
import java.util.ArrayList;

public class SolarSystemSimulation extends Group {
    private SolarSystem solarSystem;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;

    public SolarSystemSimulation(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;

        for (DisplayedCelestialBody displayedBody : solarSystem.getBodies()) {
            CelestialBody body = displayedBody.getCelestialBody();
            Sphere sphere = displayedBody.getDisplayObject();
            PhongMaterial material = new PhongMaterial();
            Image texture = loadImage(body.getTextureFilename());
            if (texture != null) {
                material.setDiffuseMap(texture);
            }
            sphere.setMaterial(material);
            getChildren().add(sphere);
        }

        // Wypisanie promieni sfer
        for (DisplayedCelestialBody displayedBody : solarSystem.getBodies()) {
            CelestialBody body = displayedBody.getCelestialBody();
            Sphere sphere = displayedBody.getDisplayObject();
            System.out.println("Promień " + body.getName() + ": " + sphere.getRadius());
        }
    }

    public void setRadius(DisplayedCelestialBody displayedBody, double radius) {
        Sphere sphere = displayedBody.getDisplayObject();
        sphere.setRadius(radius);
    }

    private Image loadImage(String filename) {
        InputStream inputStream = getClass().getResourceAsStream("/" + filename);
        return new Image(inputStream);
    }

    public void updatePositions(double deltaTime) {
        List<DisplayedCelestialBody> bodies = solarSystem.getBodies();
        for (int i = 1; i < bodies.size(); i++) {
            DisplayedCelestialBody displayedBody = bodies.get(i);
            CelestialBody body = displayedBody.getCelestialBody();

            // Prędkość kątowa każdego ciała niebieskiego
            // Tutaj mnożymy wartość prędkości kątowej przez 0.1, aby zmniejszyć ją o jedną dziesiątą
            double angularSpeed = 0.1 * 2 * Math.PI / (body.getOrbitalSpeed() * 60);

            // Pomijamy aktualizacje pozycji dla "fixed" obiektów
            if (body.isFixed()) {
                continue;
            }

            // Obliczenie nowej pozycji ciała niebieskiego
            Vector3D currentPosition = body.getPosition();
            double angle = angularSpeed * deltaTime;
            double newX = currentPosition.getX() * Math.cos(angle) - currentPosition.getZ() * Math.sin(angle);
            double newZ = currentPosition.getX() * Math.sin(angle) + currentPosition.getZ() * Math.cos(angle);
            Vector3D newPosition = new Vector3D(newX, currentPosition.getY(), newZ);

            body.setPosition(newPosition);
        }
    }

    public void start() {
        setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = getRotate();
            anchorAngleY = getRotate();

        });

        setOnMouseDragged(event -> {
            getTransforms().clear();
            Rotate rotateX = new Rotate(anchorAngleX + (event.getSceneY() - anchorY), Rotate.X_AXIS);
            Rotate rotateY = new Rotate(anchorAngleY - (event.getSceneX() - anchorX), Rotate.Y_AXIS);
            getTransforms().addAll(rotateX, rotateY);

        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Obliczamy różnicę czasu w sekundach
                double deltaTime = 1.0 / 60.0;

                updatePositions(deltaTime);

                List<DisplayedCelestialBody> sortedDisplayedBodies = new ArrayList<>(solarSystem.getBodies());
                sortedDisplayedBodies.sort((body1, body2) -> {
                    CelestialBody celestialBody1 = body1.getCelestialBody();
                    CelestialBody celestialBody2 = body2.getCelestialBody();
                    if (celestialBody1.getName().equals("Sun")) return 1;
                    else if (celestialBody2.getName().equals("Sun")) return -1;
                    else {
                        Sphere sphere1 = body1.getDisplayObject();
                        Sphere sphere2 = body2.getDisplayObject();
                        double distance1 = calculateDistanceToCamera(sphere1);
                        double distance2 = calculateDistanceToCamera(sphere2);
                        return Double.compare(distance2, distance1);
                    }
                });

                getChildren().clear();

                for (DisplayedCelestialBody displayedBody : sortedDisplayedBodies) {
                    CelestialBody body = displayedBody.getCelestialBody();
                    Sphere sphere = displayedBody.getDisplayObject();
                    sphere.setTranslateX(body.getPosition().getX());
                    sphere.setTranslateY(body.getPosition().getY());
                    sphere.setTranslateZ(body.getPosition().getZ());

                    body.updateRotation(deltaTime);

                    if (!body.getName().equals("Sun")) {
                        sphere.getTransforms().clear();
                        Rotate rotate = new Rotate(body.getRotationAngle(), Rotate.Y_AXIS);
                        sphere.getTransforms().add(rotate);
                    }

                    getChildren().add(sphere);
                }
            }
        }.start();
    }


    private double calculateDistanceToCamera(Sphere sphere) {
        double cameraX = 0;  // Podstawowe wartości, jeśli kamera jest na pozycji (0,0,0)
        double cameraY = 0;
        double cameraZ = 0;
        return Math.sqrt(Math.pow(sphere.getTranslateX() - cameraX, 2) +
                Math.pow(sphere.getTranslateY() - cameraY, 2) +
                Math.pow(sphere.getTranslateZ() - cameraZ, 2));
    }
}