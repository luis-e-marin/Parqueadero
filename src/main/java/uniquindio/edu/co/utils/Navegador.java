package uniquindio.edu.co.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegador {

    public static void irA(String rutaFXML, Stage stageActual) {
        try {
            if (!rutaFXML.startsWith("/")) {
                rutaFXML = "/view/" + rutaFXML;
            }

            var resource = Navegador.class.getResource(rutaFXML);
            if (resource == null) {
                throw new RuntimeException("No se encontró el FXML: " + rutaFXML);
            }

            FXMLLoader loader = new FXMLLoader(resource);
            Parent root = loader.load();

            stageActual.setScene(new Scene(root));
            stageActual.centerOnScreen();

        } catch (Exception exception) {
            System.err.println("ERROR AL CARGAR: " + rutaFXML);
            exception.printStackTrace();
        }
    }

    public static void volverAlAdmin(Stage stage) {
        irA("AdminView.fxml", stage);
    }

    public static void volverAlOperador(Stage stage) {
        irA("OperadorMenu.fxml", stage);
    }

    public static void cerrarSesion(Stage stage) {
        irA("LoginView.fxml", stage);
    }
}
