package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import modelo.Piloto;
import util.Conexion;

public class ControladorPiloto {
    @FXML
    private TableView<Piloto> pilotoTabla;
    @FXML
    private TableColumn<Piloto, String> licenciaColumna;
    
    
    //private static final String DELETE_PILOTO = "delete from pilotos  where nombre = ? and apellidos = ?";
    
    @FXML
    private Label nombreEtiqueta;
    @FXML
    private Label apellidosEtiqueta;
    @FXML
    private Label contraseniaEtiqueta;
    @FXML
    private Label clubEtiqueta;
    @FXML
    private Label emailEtiqueta;
    @FXML
    private Label licenciaEtiqueta;
    @FXML
    private Label paisEtiqueta;
    @FXML
    private Label calleEtiqueta;
    @FXML
    private Label ciudadEtiqueta;
    @FXML
    private Label provinciaEtiqueta;
    @FXML
    private Label telefonoEtiqueta;
    @FXML
    private Label codigoPostalEtiqueta;
    
    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ControladorPiloto() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        licenciaColumna.setCellValueFactory(
                cellData -> cellData.getValue().nombreProperty());

        // Clear person details.
        showPilotoDetails(null);

        // Listen for selection changes and show the person details when changed.
        pilotoTabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPilotoDetails(newValue));
    
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        pilotoTabla.setItems(mainApp.getPilotoData());
    }
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showPilotoDetails(Piloto piloto) {
        if (piloto != null) {
            // Fill the labels with info from the person object.
        	nombreEtiqueta.setText(piloto.getNombre());
        	apellidosEtiqueta.setText(piloto.getApellidos());
        	contraseniaEtiqueta.setText(piloto.getContrasenia());
        	clubEtiqueta.setText(piloto.getClub());
        	emailEtiqueta.setText(piloto.getEmail());
        	licenciaEtiqueta.setText(piloto.getLicencia());
        	paisEtiqueta.setText(piloto.getPais());
        	calleEtiqueta.setText(piloto.getCalle());
        	ciudadEtiqueta.setText(piloto.getCiudad());
        	provinciaEtiqueta.setText(piloto.getProvincia());
        	telefonoEtiqueta.setText(Integer.toString(piloto.getTelefono()));
        	codigoPostalEtiqueta.setText(Integer.toString(piloto.getCodigoPostal()));
        } else {
            // Person is null, remove all the text.
        	nombreEtiqueta.setText("");
        	apellidosEtiqueta.setText("");
        	contraseniaEtiqueta.setText("");
        	clubEtiqueta.setText("");
        	emailEtiqueta.setText("");
        	licenciaEtiqueta.setText("");
        	paisEtiqueta.setText("");
        	calleEtiqueta.setText("");
        	ciudadEtiqueta.setText("");
        	provinciaEtiqueta.setText("");
        	telefonoEtiqueta.setText("");
        	codigoPostalEtiqueta.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    
    @FXML
    private void handleDeletePerson() {
    	int selectedIndex = pilotoTabla.getSelectionModel().getSelectedIndex();
    	//recogemos los datos necesarios para realizar los criterios de la consulta
    	String nombre =(String) pilotoTabla.getSelectionModel().getSelectedItem().getNombre();
    	if (selectedIndex >= 0) {
            pilotoTabla.getItems().remove(selectedIndex);
         // conectamos con la base de datos, si no existe se crea
            ODB odb = ODBFactory.open("VUELOS.DB");
         // Cogemos los criterios para la consulta
            ICriterion criterio = new And().add(Where.equal("licencia", licenciaColumna));
         // Hacemos la consulta 
            IQuery query = new CriteriaQuery(Piloto.class, criterio);
         // Cargamos los objetos que coincidan con esa consulta
            Objects<Piloto> objects = odb.getObjects(query);
         // Nos posicionamos en el primer resultado
            Piloto pil=(Piloto) objects.getFirst();
         // Y lo borramos
            odb.delete(pil);
         //cerramos la conexión con la base de datos
            odb.close();
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
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void nuevoPiloto() {
        Piloto tempPiloto = new Piloto();
        //boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        boolean okClicked = mainApp.showPilotoNuevaDialogo(tempPiloto);
        if (okClicked) {
            mainApp.getPilotoData().add(tempPiloto);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void editarPiloto() {
    	Piloto selectedPiloto = pilotoTabla.getSelectionModel().getSelectedItem();
        if (selectedPiloto != null) {
            boolean okClicked = mainApp.showPilotoEditDialog(selectedPiloto);
            if (okClicked) {
                showPilotoDetails(selectedPiloto);
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
