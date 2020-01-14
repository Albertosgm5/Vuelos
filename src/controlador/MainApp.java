package controlador;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ListaPersonaXML;
import modelo.Persona;
import util.Conexion;
import javafx.stage.Stage;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;

public class MainApp extends Application{
	private Stage primaryStage;
    private BorderPane rootLayout;
    private static final String CREATE_VUELOS = "insert into pilotos (nombre, apellidos, contraseña, club, email, cumple) values (?,?,?,?,?,?)";
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Persona> personaData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        personaData.add(new Persona("Hans", "Muster"));
        personaData.add(new Persona("Ruth", "Mueller"));
        personaData.add(new Persona("Heinz", "Kurz"));
        personaData.add(new Persona("Cornelia", "Meier"));
        personaData.add(new Persona("Werner", "Meyer"));
        personaData.add(new Persona("Lydia", "Kunz"));
        personaData.add(new Persona("Anna", "Best"));
        personaData.add(new Persona("Stefan", "Meier"));
        personaData.add(new Persona("Martin", "Mueller"));
    }
  
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Persona> getPersonaData() {
        return personaData;
    }
  

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AgendaApp");
        
        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:resources/images/icono.png"));

        initRootLayout();

        showPersonOverview();
        
    }
    
    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("../vista/Raiz.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            ControladorRaiz controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getPersonFilePath();
        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/Persona.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            ControladorPersona controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Persona persona) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/EditarPersona.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControladorEditarPersona controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(persona);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showPersonaNuevaDialogo(Persona persona) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/NuevaPersona.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nueva Persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControladorNuevaPersona controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(persona);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     * 
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }
    
    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     * 
     * @param file
     */
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListaPersonaXML.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ListaPersonaXML xml = (ListaPersonaXML) um.unmarshal(file);

            personaData.clear();
            personaData.addAll(xml.getPersons());

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No puedo cargar los datos");
            alert.setContentText("No puedo cargar los datos del fichero:\n" + file.getPath());

            alert.showAndWait();
        }
    }
   

    /**
     * Saves the current person data to the specified file.
     * 
     * @param file
     */
    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ListaPersonaXML.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            ListaPersonaXML xml = new ListaPersonaXML();
            xml.setPersons(personaData);

            // Marshalling and saving XML to the file.
            m.marshal(xml, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No puedo guardar los datos");
            alert.setContentText("No puedo guardar los datos al fichero:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    
    public void savePersonDataToMysql() {
    	PreparedStatement stmt = null;
    	Conexion conexion = new Conexion();
        try {
        	
        	ListaPersonaXML list = new ListaPersonaXML();
            list.setPersons(personaData);
        	for(int i=0;i<list.getPersons().size();i++) {
        		stmt = conexion.dameConexion().prepareStatement(CREATE_AGENDA);
        		Persona p = list.getPersons().get(i);
        		stmt.setString(1, p.getFirstName());
      			stmt.setString(2, p.getLastName());
      			stmt.setString(3, p.getStreet());
      			stmt.setInt(4, p.getPostalCode());
      			stmt.setString(5, p.getCity());
      			Date date = java.sql.Date.valueOf(p.getBirthday());
      			stmt.setDate(6, (java.sql.Date) date);
      			stmt.execute();
        	}

        	stmt.close();
			
        } catch (Exception e) { 
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No puedo cargar los datos");
            alert.setContentText("No puedo cargar los datos a la base de datos");
            System.err.print(e);
            alert.showAndWait();
        }
    }
    public void loadPersonDataFromNeodatis() {
    	
        try {
        	//abrimos la conexión con la base de datos
        	ODB odb = ODBFactory.open("AGENDA.DB");
        	// Genero un conjunto de objetos y los traigo del ODB conectado
        	Objects objectsAgen = odb.getObjects(Persona.class);
        	//borro de la aplicación los datos
        	personaData.clear();
        	// Mientras haya objetos, los capturo y muestro
        	while(objectsAgen.hasNext()){
        		// Creo un objeto Persona y almaceno ahí el objeto
        		Persona p = (Persona) objectsAgen.next();
        		//agrego los nuevos datos cargados de neodatis a la aplicación
                personaData.add(p);
                 
        	}
        	//cierro conexión con la base de datos
         	odb.close();


        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No puedo cargar los datos");
            alert.setContentText("No puedo cargar los datos de la base de datos");

            alert.showAndWait();
        }
    }
    public void savePersonDataNeodatis() {
    	 try {
    		 /*La conexión la realizo con un objeto de clase ODB,
    		 en la que indico la ruta donde tengo la base de datos.
    		 Esto sirve para abrirla como para crear una nueva*/
    		ODB odb = ODBFactory.open("AGENDA.DB");
    		
         	ListaPersonaXML list = new ListaPersonaXML();
            list.setPersons(personaData);
         	for(int i=0;i<list.getPersons().size();i++) {
         		Persona p = list.getPersons().get(i);
         		Persona p1 = new Persona(p.getFirstName(), p.getLastName());
         		p1.setStreet(p.getStreet());
         		p1.setPostalCode(p.getPostalCode());
         		p1.setCity(p.getCity());
         		p1.setBirthday(p.getBirthday());
         		odb.store(p1);
         	}
         	odb.close();

 			
         } catch (Exception e) { 
             Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error");
             alert.setHeaderText("No puedo cargar los datos");
             alert.setContentText("No puedo cargar los datos a la base de datos");
             System.err.print(e);
             alert.showAndWait();
         }
    }
  
    /**
     * Opens a dialog to show birthday statistics.
     */
    public void showBirthdayStatistics() {
        try {
            // Load the fxml file and create a new stage for the popup.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/EstadisticasCumple.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Birthday Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the persons into the controller.
            ControladorEstadisticasCumple controller = loader.getController();
            controller.setPersonData(personaData);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}