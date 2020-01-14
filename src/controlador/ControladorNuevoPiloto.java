package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.Fecha;
import org.controlsfx.dialog.Dialogs;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import modelo.Piloto;

public class ControladorNuevoPiloto {
	@FXML
    private TextField nombreCampo;
    @FXML
    private TextField apellidosCampo;
    @FXML
    private java.awt.TextField contraseniaCampo;
    @FXML
    private TextField clubCampo;
    @FXML
    private TextField emailCampo;
    @FXML
    private TextField licenciaCampo;
    @FXML
    private TextField paisCampo;
    @FXML
    private TextField calleCampo;
    @FXML
    private TextField ciudadCampo;
    @FXML
    private TextField provinciaCampo;
    @FXML
    private TextField telefonoCampo;
    @FXML
    private TextField codigoPostalCampo;
    


    private Stage dialogStage;
    private Piloto piloto;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;

        nombreCampo.setText(piloto.getNombre());
        apellidosCampo.setText(piloto.getApe());
        contraseniaCampo.setText(piloto.get);
        calleCampo.setText(piloto.getStreet());
        codigoPostalCampo.setText(Integer.toString(piloto.getPostalCode()));
        ciudadCampo.setText(person.getCity());
        
       
        private TextField clubCampo;
        @FXML
        private TextField emailCampo;
        @FXML
        private TextField licenciaCampo;
        @FXML
        private TextField paisCampo;
        @FXML
        private TextField calleCampo;
        @FXML
        private TextField ciudadCampo;
        @FXML
        private TextField provinciaCampo;
        @FXML
        private TextField telefonoCampo;
        @FXML
        private TextField codigoPostalCampo;
       
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(nombreCampo.getText());
            person.setLastName(apellidosCampo.getText());
            person.setStreet(calleCampo.getText());
            person.setPostalCode(Integer.parseInt(codigoPostalCampo.getText()));
            person.setCity(ciudadCampo.getText());
            person.setBirthday(Fecha.parse(cumpleCampo.getText()));
           
            ODB odb = ODBFactory.open("AGENDA.DB");
            Piloto p1 = new Piloto();
            p1.setFirstName(nombreCampo.getText());
            p1.setLastName(apellidosCampo.getText());
    		p1.setStreet(calleCampo.getText());
    		p1.setPostalCode(Integer.parseInt(codigoPostalCampo.getText()));
    		p1.setCity(ciudadCampo.getText());
    		p1.setBirthday(Fecha.parse(cumpleCampo.getText()));
    		odb.store(p1);
    		
            okClicked = true;
            dialogStage.close();
            odb.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreCampo.getText() == null || nombreCampo.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (apellidosCampo.getText() == null || apellidosCampo.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (ciudadCampo.getText() == null || ciudadCampo.getText().length() == 0) {
            errorMessage += "No valid street!\n"; 
        }

        if (codigoPostalCampo.getText() == null || codigoPostalCampo.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(codigoPostalCampo.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (ciudadCampo.getText() == null || ciudadCampo.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (cumpleCampo.getText() == null || cumpleCampo.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!Fecha.validDate(cumpleCampo.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            
            return false;
        }
    }
}
