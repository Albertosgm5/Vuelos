package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

import modelo.Piloto;
import util.Conexion;

import modelo.ListaPilotosXML;



public class ControladorSesion {
	@FXML
    private TextField licenciaCampo;
    
    private TextField contrasenaCampo;
    
    private Stage dialogStage;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

	/**
	 * Called when the user clicks ok.
	 */
@FXML
	private void inicioSesion() {
		String errorMessage = "";

		boolean existe = false;
		ODB odb = ODBFactory.open("VUELOS.DB");
		Objects<Piloto> pilotos = odb.getObjects(Piloto.class);
		while (pilotos.hasNext() && !existe) {
			Piloto p = pilotos.next();
			if (p.getLicencia().equalsIgnoreCase(licenciaCampo.getText())
					&& p.getContrasenia().equals(contrasenaCampo.getText())) {
				existe = true;
			}
		}
		odb.close();

		if (!existe) {
			errorMessage += "El usuario no existe\n";

		}
		
		okClicked = true;
        dialogStage.close();
	}
@FXML
	private void handleClose() {
	        dialogStage.close();
	    }

}
