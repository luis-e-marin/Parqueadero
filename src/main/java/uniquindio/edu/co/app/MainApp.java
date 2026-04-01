package uniquindio.edu.co.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlUrl = getClass().getResource("/view/LoginView.fxml");

            if (fxmlUrl == null) {
                System.err.println("ERROR: No se encontró LoginView.fxml");
                System.err.println("Verifica que el archivo esté en: src/main/resources/view/LoginView.fxml");
                throw new RuntimeException("Archivo FXML no encontrado");
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load(), 450, 350);

            stage.setTitle("PARKUQ - Sistema de Parqueadero");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();

            System.out.println("✓ Aplicación iniciada correctamente - Login cargado");

        } catch (Exception e) {
            System.err.println("=== ERROR CRÍTICO AL INICIAR LA APLICACIÓN ===");
            e.printStackTrace();

           
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Error de Inicio");
            alert.setHeaderText("No se pudo iniciar PARKUQ");
            alert.setContentText("Error al cargar la pantalla de login.\n\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}