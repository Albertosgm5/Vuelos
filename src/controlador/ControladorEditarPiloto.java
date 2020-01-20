package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.ListaPilotosXML;

import modelo.Piloto;
import util.Conexion;


public class ControladorEditarPiloto {
	
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
    
    private MainApp mainApp;
    private Stage dialogStage;
    private Piloto piloto;
    private boolean okClicked = false;
    
    @FXML
    private TableView<Piloto> pilotoTabla;
    @FXML
    private TableColumn<Piloto, String> nombreColumna;
    @FXML
    private TableColumn<Piloto, String> apellidosColumna;
    
    private static final String EDIT_PILOTO = "update pilotos set nombre = ?, apellidos = ?, contraseña = ?, club = ?, email = ?, licencia = ?, pais = ?, calle = ?, ciudad = ?, provincia = ? , telefono = ?, codigoPostal = ? where nombre = ? and apellidos = ?";
    @FXML
    private void initialize() {
    }



    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;

        nombreCampo.setText(piloto.getNombre());
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
        codigoPostalCampo.setText(Integer.toString(piloto.getCodigoPostal()));
        
       
    }


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
        	piloto.setNombre(nombreCampo.getText());
            piloto.setApellidos(apellidosCampo.getText());
            piloto.setContrasenia(contraseniaCampo.getText());
            piloto.setEmail(emailCampo.getText());
            piloto.setLicencia(licenciaCampo.getText());
            piloto.setPais(paisCampo.getText());
            piloto.setCalle(calleCampo.getText());
            piloto.setCiudad(ciudadCampo.getText());
            piloto.setProvincia(provinciaCampo.getText());
            piloto.setTelefono(Integer.parseInt(telefonoCampo.getText()));
            piloto.setCodigoPostal(Integer.parseInt(codigoPostalCampo.getText()));
           
            int selectedIndex = pilotoTabla.getSelectionModel().getSelectedIndex();
        	//recogemos los datos necesarios para realizar los criterios de la consulta
        	String nombre =(String) pilotoTabla.getSelectionModel().getSelectedItem().getNombre();
        	String apellidos =(String) pilotoTabla.getSelectionModel().getSelectedItem().getApellidos();
        	if (selectedIndex >= 0) {
        		
            PreparedStatement stmt = null;
        	Conexion conexion = new Conexion();
        	try {
            	ListaPilotosXML list = new ListaPilotosXML();
                list.setPiloto(MainApp.pilotoData);
            	for(int i=0;i<list.getPiloto().size();i++) {
            		stmt = conexion.dameConexion().prepareStatement(EDIT_PILOTO);
            		Piloto p = list.getPiloto().get(i);
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
          			stmt.setString(13, nombre);
        			stmt.setString(14, apellidos);
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
        	} else {
                // Nothing selected.
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(mainApp.getPrimaryStage());
                alert.setTitle("No seleccionado");
                alert.setHeaderText("No has seleccionado persona");
                alert.setContentText("Por favor selecciona una persona de la tabla.");

                alert.showAndWait();
            }
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
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
