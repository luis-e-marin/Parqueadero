package uniquindio.edu.co.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegador {

    public static void irA(String rutaFXML, Stage stageActual) {
        try {
            if (!rutaFXML.startsWith("/")) {
                rutaFXML = "/" + rutaFXML;
            }

            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(rutaFXML));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stageActual.setScene(scene);
            stageActual.centerOnScreen();
            stageActual.show();

        } catch (Exception e) {
            System.err.println("Error al cargar: " + rutaFXML);
            e.printStackTrace();
        }
    }

    // Método cómodo para volver al Admin
    public static void volverAlAdmin(Stage stageActual) {
        irA("/view/AdminView.fxml", stageActual);
    }
}