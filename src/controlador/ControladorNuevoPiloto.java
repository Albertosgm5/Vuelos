package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import modelo.Piloto;
import util.Conexion;

import modelo.ListaPilotosXML;



public class ControladorNuevoPiloto {
	@FXML
    private TextField nombreCampo;
    @FXML
    private TextField apellidosCampo;
    @FXML
    private TextField contraseniaCampo;
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
    
    private static final String CREATE_PILOTO = "insert into pilotos (nombre, apellidos, contrasena, club, email, licencia, pais, calle, ciudad, provincia, telefono, codigoPostal) values (?,?,?,?,?,?,?,?,?,?,?,?)";

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
        
        /*nombreCampo.setText(piloto.getNombre());
        
        apellidosCampo.setText(piloto.getApellidos());
        
        contraseniaCampo.setText(piloto.getContrasenia());
       
        clubCampo.setText(piloto.getClub());
        
        emailCampo.setText(piloto.getEmail());
        
        licenciaCampo.setText(piloto.getLicencia());
       
        paisCampo.setText(piloto.getPais());
        
        calleCampo.setText(piloto.getCalle());
        
        ciudadCampo.setText(piloto.getCiudad());
        
        provinciaCampo.setText(piloto.getProvincia());
        
        telefonoCampo.setText(Integer.toString(piloto.getTelefono()));
        
        codigoPostalCampo.setText(Integer.toString(piloto.getCodigoPostal()));*/
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
            piloto.setNombre(nombreCampo.getText());
            piloto.setApellidos(apellidosCampo.getText());
            piloto.setContrasenia(contraseniaCampo.getText());
            piloto.setClub(clubCampo.getText());
            piloto.setEmail(emailCampo.getText());
            piloto.setLicencia(licenciaCampo.getText());
            piloto.setPais(paisCampo.getText());
            piloto.setCalle(calleCampo.getText());
            piloto.setCiudad(ciudadCampo.getText());
            piloto.setProvincia(provinciaCampo.getText());
            piloto.setTelefono(Integer.parseInt(telefonoCampo.getText()));
            piloto.setCodigoPostal(Integer.parseInt(codigoPostalCampo.getText()));
           
            
            ODB odb = ODBFactory.open("VUELOS.DB");
           
    		odb.store(piloto);
    		
            okClicked = true;
            dialogStage.close();
            odb.close();
            
            /*
            Conexion conexion = new Conexion();
            PreparedStatement stmt = null;
            try {
            	ListaPilotosXML list = new ListaPilotosXML();
                list.setPiloto(MainApp.pilotoData);
                for(int i=0;i<list.getPiloto().size();i++) {
            		stmt = conexion.dameConexion().prepareStatement(CREATE_PILOTO);
            		Piloto p = (Piloto) list.getPiloto().get(i);
            		stmt.setString(1, p.getNombre());
          			stmt.setString(2, p.getApellidos());
          			stmt.setString(3, p.getContrasenia());
          			stmt.setString(4, p.getClub());
          			stmt.setString(5, p.getEmail());
          			stmt.setString(6, p.getLicencia());
          			stmt.setString(7, p.getPais());
          			stmt.setString(8, p.getCalle());
          			stmt.setString(9, p.getCiudad());
          			stmt.setString(10, p.getProvincia());
          			stmt.setInt(11, p.getTelefono());
          			stmt.setInt(12, p.getCodigoPostal());
          			stmt.execute();
                }

            	stmt.close();
    		
            	okClicked = true;
            	dialogStage.close();
            
            } catch (Exception e) { 
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No puedo cargar los datos");
                alert.setContentText("No puedo cargar los datos a la base de datos");
                System.err.print(e);
                alert.showAndWait();
            }
            */
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
