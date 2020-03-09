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

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import modelo.Concurso;
import modelo.ListaPilotosXML;

import modelo.Piloto;
import util.Conexion;
import util.Fecha;


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
    private TableColumn<Piloto, String> licenciaColumna;
    
    //private static final String EDIT_PILOTO = "update pilotos set nombre = ?, apellidos = ?, contrasena = ?, club = ?, email = ?, licencia = ?, pais = ?, calle = ?, ciudad = ?, provincia = ? , telefono = ?, codigoPostal = ? where licencia = ?";
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
    	//if (isInputValid()) {
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
       
        // conectamos con la base de datos, si no existe se crea
        ODB odb = ODBFactory.open("VUELOS.DB");
        // Cogemos los criterios para la consulta
        ICriterion criterio = new And().add(Where.equal("licencia", licenciaCampo.getText()));
     // Hacemos la consulta 
        IQuery query = new CriteriaQuery(Concurso.class, criterio);
     // Cargamos los objetos que coincidan con esa consulta
        Objects<Concurso> objects = odb.getObjects(query);
     // Nos posicionamos en el primer resultado
        Concurso concurso=(Concurso) objects.getFirst();
     // A la persona sobre la que hacemos la consulta le pasamos sus nuevos datos
     // Con esto conseguimos modificar sus datos en la base de datos
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
		odb.store(piloto);
		
        okClicked = true;
        dialogStage.close();
        odb.close();
        
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
