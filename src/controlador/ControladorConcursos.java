package controlador;

import javafx.fxml.FXML;

public class ControladorConcursos {
	private MainApp mainApp;
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
	}
	
	@FXML
	private void concurso() {
		this.mainApp.showConcursos();
		
	}
}
