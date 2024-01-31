import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.shape.Sphere;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SolarSystem solarSystem = initSolarSystem();
        SolarSystemSimulation simulation = new SolarSystemSimulation(solarSystem);

        // Stworzenie AnchorPane i dodanie do niego symulacji
        AnchorPane root = new AnchorPane();
        root.getChildren().add(simulation);
        AnchorPane.setTopAnchor(simulation, 0.0);
        AnchorPane.setBottomAnchor(simulation, 0.0);
        AnchorPane.setLeftAnchor(simulation, 0.0);
        AnchorPane.setRightAnchor(simulation, 0.0);

        // Wczytanie obrazu tła i ustawienie go jako tła dla sceny
        Image spaceBackground = new Image(getClass().getResource("/space_background.jpg").toExternalForm());
        ImagePattern imagePattern = new ImagePattern(spaceBackground);
        BackgroundFill fill = new BackgroundFill(imagePattern, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(fill);
        root.setBackground(background);

        // Ustawienie sceny na pełny ekran na głównym monitorze
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        Scene scene = new Scene(root, 1200, 900);
        scene.setFill(Color.BLACK); // Ustaw czarne tło dla całej sceny

        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(0.000001);
        camera.setFarClip(9000.0);
        camera.setTranslateZ(-30);
        camera.setRotate(0); // Dodaj obroty
        camera.setTranslateY(0); // Dodaj przesunięcie
        camera.setTranslateX(330); // Dodaj przesunięcie
        scene.setCamera(camera);

        // Przesuwanie symulacji, aby środek symulacji pokrywał się ze środkiem ekranu
        simulation.setTranslateX(scene.getWidth() / 2);
        simulation.setTranslateY(scene.getHeight() / 2);

        stage.setScene(scene);
        stage.show();

        simulation.start();

        // Ustawienie większego promienia dla słońca
        DisplayedCelestialBody sun = solarSystem.getBodies().get(0);
        sun.getCelestialBody().setRadius(1000);

        // Dodanie obsługi zdarzenia scroll na scenie
        scene.setOnScroll(event -> {
            double delta = event.getDeltaY();
            camera.setTranslateZ(camera.getTranslateZ() + delta);
        });
    }

    private SolarSystem initSolarSystem() {
        SolarSystem solarSystem = new SolarSystem();

        double earthRadius = 1.0;
        double oneAU = 50.0;
        double earthOrbitalPeriod = 365.25;  // Okres orbitalny Ziemi

        Vector3D zeroVelocity = new Vector3D(0, 0, 0);

        // Ustawiamy Słońce w środku układu
        double sunMass = 1.989 * Math.pow(10, 30);
        CelestialBody sunBody = new CelestialBody("Sun", sunMass, 15.0 * earthRadius, new Vector3D(0, 0, 0), zeroVelocity, "sun.jpg", 0.0,true);
        Sphere sunSphere = new Sphere(sunBody.getRadius());
        DisplayedCelestialBody sun = new DisplayedCelestialBody(sunBody, sunSphere);
        solarSystem.addBody(sun);

        // Dodajemy resztę ciał niebieskich
        String[] planetNames = {"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
        double[] planetSizes = {0.383, 0.949, 1.0, 0.532, 11.21, 9.45, 4.01, 3.88};
        double[] distancesFromSun = {0.39, 0.723, 1.0, 1.52, 5.20, 9.58, 19.18, 30.07};
        double[] planetMasses = {3.3011 * Math.pow(10, 23), 4.8675 * Math.pow(10, 24), 5.97237 * Math.pow(10, 24), 6.4171 * Math.pow(10, 23), 1.8982 * Math.pow(10, 27), 5.6834 * Math.pow(10, 26), 8.6810 * Math.pow(10, 25), 1.02413 * Math.pow(10, 26)};  // W kg

        for (int i = 0; i < planetNames.length; i++) {
            double angle = i * (360.0 / planetNames.length); // Rozłożenie planet na okręgu
            double x = distancesFromSun[i]*oneAU * Math.cos(Math.toRadians(angle));
            double z = distancesFromSun[i]*oneAU * Math.sin(Math.toRadians(angle));
            Vector3D position = new Vector3D(x, 0, z);

            // Obliczanie prędkości początkowej planety. Prędkość jest prostopadła do wektora położenia i ma wielkość proporcjonalną do odwrotności okresu obiegu.
            double orbitalPeriod = 5 * Math.pow(distancesFromSun[i], 1.5);  // Zgodnie z prawami Keplera i pomnożone przez 5
            double velocityMagnitude = 2 * Math.PI * oneAU * distancesFromSun[i] / (60 * orbitalPeriod);  // Uwaga: zakładamy, że jednostką czasu jest minuta, a nie dzień
            Vector3D velocity = new Vector3D(-velocityMagnitude * Math.sin(Math.toRadians(angle)), 0, velocityMagnitude * Math.cos(Math.toRadians(angle)));


            CelestialBody body = new CelestialBody(planetNames[i], planetMasses[i], planetSizes[i]*earthRadius, position, velocity, planetNames[i].toLowerCase() + ".jpg", orbitalPeriod / earthOrbitalPeriod, false);

            Sphere sphere = new Sphere(body.getRadius());
            DisplayedCelestialBody displayedBody = new DisplayedCelestialBody(body, sphere);
            solarSystem.addBody(displayedBody);
        }

        return solarSystem;
    }


    public static void main(String[] args) {
        launch(args);
    }
}