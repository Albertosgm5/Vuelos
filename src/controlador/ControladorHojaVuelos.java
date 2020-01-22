package controlador;

import javafx.fxml.FXML;

public class ControladorHojaVuelos {

    private MainApp mainApp;
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
	}
	
	@FXML
	private void hojaVuelos() {
		this.mainApp.showHojaVuelos();
		
	}
}
