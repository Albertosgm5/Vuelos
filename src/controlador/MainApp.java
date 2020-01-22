package controlador;


import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Piloto;


public class MainApp extends Application{
	private Stage primaryStage;
    private BorderPane rootLayout;
    //private static final String CREATE_VUELOS = "insert into pilotos (nombre, apellidos, contraseña, club, email, licencia, pais, calle, ciudad, provincia, telefono, codigo postal) values (?,?,?,?,?,?,?,?,?,?,?)";
    /**
     * The data as an observable list of Persons.
     */
    static ObservableList<Piloto> pilotoData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        pilotoData.add(new Piloto("Hans", "Muster Fuster", "AA45", "NNSS", "hans@gmail.com", "A2345778G", "Inglaterra", "Carcassonne 45 4F", "Cambridge", "Londres", 967785679, 5555555));
    }
  
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Piloto> getPilotoData() {
        return pilotoData;
    }
  

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("VuelosApp");
       

        initRootLayout();

        showPilotoOverview();
        
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
      
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPilotoOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/Home.fxml"));
            AnchorPane pilotoOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(pilotoOverview);

            // Give the controller access to the main app.
            ControladorHome controller = loader.getController();
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
    public boolean showPilotoEditDialog(Piloto piloto) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/Editar.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Piloto");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControladorEditarPiloto controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPiloto(piloto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showPilotoNuevaDialogo(Piloto piloto) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../vista/Altas.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nuevo Piloto");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ControladorNuevoPiloto controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPiloto(piloto);

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

	public void loadPilotoDataFromMysql() {
		// TODO Auto-generated method stub
		
	}

    /*public void savePilotoDataToMysql() {
    	PreparedStatement stmt = null;
    	Conexion conexion = new Conexion();
        try {
        	ListaPilotoXML list = new ListaPilotoXML();
            list.setPiloto(pilotoData);
        	for(int i=0;i<list.getPiloto().size();i++) {
        		stmt = conexion.dameConexion().prepareStatement(CREATE_VUELOS);
        		Piloto p = list.getPiloto().get(i);
        		stmt.setString(1, p.getFirstName());
      			stmt.setString(2, p.getLastName());
      			stmt.setString(3, p.getStreet());
      			stmt.setInt(4, p.getPostalCode());
      			stmt.setString(5, p.getCity());
      			stmt.setDate(6, (java.sql.Date) date);
      			stmt.setInt(4, p.getPostalCode());
      			stmt.setString(5, p.getCity());
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
    }*/
 
   
   
}