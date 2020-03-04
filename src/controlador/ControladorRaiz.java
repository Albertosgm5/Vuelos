package controlador;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import modelo.Piloto;


public class ControladorRaiz {
	// Reference to the main application
    private MainApp mainApp;
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        mainApp.getPilotoData().clear();
      
    }
    
    
    @FXML
	private void hojaVuelos() {
		this.mainApp.showHojaVuelos();
		
	}
    @FXML
    private void concursos() {
		this.mainApp.showConcursos();
		
	}
    @FXML
    private void sesion() {
		this.mainApp.showSesion();
		
	}

    @FXML
    private void home() {
		this.mainApp.showPilotoOverview();
		
	}
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
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        /*FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            //mainApp.loadPersonDataFromFile(file);
        }*/
        mainApp.loadPilotoDataFromMysql();
    }

   
   
   

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("AgendaXML");
        alert.setHeaderText("About");
        alert.setContentText("Author: Marco Jakob\nWebsite: http://code.makery.ch");

        alert.showAndWait();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
 
}
