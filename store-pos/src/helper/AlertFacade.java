package helper;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertFacade {
    private static AlertHelper alertHelper = new AlertHelper();

    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        alertHelper.showAlert(alertType, owner, title, message);
    }
}