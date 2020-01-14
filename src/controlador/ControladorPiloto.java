package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.controlsfx.dialog.Dialogs;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ComposedExpression;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import modelo.Piloto;
import util.Fecha;

public class ControladorPiloto {
    @FXML
    private TableView<Piloto> personaTabla;
    @FXML
    private TableColumn<Piloto, String> nombreColumna;
    @FXML
    private TableColumn<Piloto, String> apellidoColumna;

    @FXML
    private Label nombreEtiqueta;
    @FXML
    private Label apellidoEtiqueta;
    @FXML
    private Label calleEtiqueta;
    @FXML
    private Label codigoPostalEtiqueta;
    @FXML
    private Label ciudadEtiqueta;
    @FXML
    private Label cumpleEtiqueta;

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
        nombreColumna.setCellValueFactory(
                cellData -> cellData.getValue().firstNameProperty());
        apellidoColumna.setCellValueFactory(
                cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personaTabla.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    
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
    private void showPersonDetails(Piloto persona) {
        if (persona != null) {
            // Fill the labels with info from the person object.
        	nombreEtiqueta.setText(persona.getFirstName());
        	apellidoEtiqueta.setText(persona.getLastName());
        	calleEtiqueta.setText(persona.getStreet());
        	codigoPostalEtiqueta.setText(Integer.toString(persona.getPostalCode()));
        	ciudadEtiqueta.setText(persona.getCity());
        	cumpleEtiqueta.setText(Fecha.format(persona.getBirthday()));
            // TODO: We need a way to convert the birthday into a String! 
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
        	nombreEtiqueta.setText("");
        	apellidoEtiqueta.setText("");
        	calleEtiqueta.setText("");
        	codigoPostalEtiqueta.setText("");
        	ciudadEtiqueta.setText("");
        	cumpleEtiqueta.setText("");
        }
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
    	int selectedIndex = personaTabla.getSelectionModel().getSelectedIndex();
    	//recogemos los datos necesarios para realizar los criterios de la consulta
    	String nombre =(String) personaTabla.getSelectionModel().getSelectedItem().getFirstName();
    	String apellido =(String) personaTabla.getSelectionModel().getSelectedItem().getLastName();
    	if (selectedIndex >= 0) {
            personaTabla.getItems().remove(selectedIndex);
         // conectamos con la base de datos, si no existe se crea
            ODB odb = ODBFactory.open("AGENDA.DB");
         // Cogemos los criterios para la consulta
            ICriterion criterio = new And().add(Where.equal("nombre", nombre)).add(Where.equal("apellido",apellido));
         // Hacemos la consulta 
            IQuery query = new CriteriaQuery(Piloto.class, criterio);
         // Cargamos los objetos que coincidan con esa consulta
            Objects<Piloto> objects = odb.getObjects(query);
         // Nos posicionamos en el primer resultado
            Piloto per=(Piloto) objects.getFirst();
         // Y lo borramos
            odb.delete(per);
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
    private void handleNewPerson() {
        Piloto tempPerson = new Piloto();
        //boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        boolean okClicked = mainApp.showPersonaNuevaDialogo(tempPerson);
        if (okClicked) {
            mainApp.getPersonaData().add(tempPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
    	Piloto selectedPerson = personaTabla.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
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
