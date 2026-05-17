module uniquindio.edu.co.parqueadero {

    requires javafx.controls;
    requires javafx.fxml;

    exports uniquindio.edu.co.app;

    opens uniquindio.edu.co.controller to javafx.fxml;

    opens uniquindio.edu.co.app to javafx.graphics;
}
