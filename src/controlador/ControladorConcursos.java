package controlador;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import modelo.Concurso;
import util.Fecha;

public class ControladorConcursos {
	private MainApp mainApp;
	
	@FXML
    private TableColumn nombre;
    @FXML
    private TableColumn tipo;
    @FXML
    private TableColumn fecha;
    @FXML
    private TableColumn lugar;
    @FXML
    private TableColumn finRe;
   
    
    private Stage dialogStage;
    private Concurso concurso;
    private boolean okClicked = false;
    
    private static final String EDIT_CONCURSO = "update concurso set concurso = ?, tipo = ?, fecha = ?, lugar = ?, finRe = ? where no = ?";
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    private void handleOk() {
       //if (isInputValid()) {
            concurso.setNombre(nombre.getText());
            concurso.setTipo(tipo.getText());
            concurso.setFecha(Fecha.parse(fecha.getText()));
            concurso.setLugar(lugar.getText());
            concurso.setFinRe(Fecha.parse(finRe.getText()));
           
            
            
            ODB odb = ODBFactory.open("VUELOS.DB");
           
    		odb.store(concurso);
    		
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
            }*/
        //}
    }
	 @FXML
    private void handleCancel() {
        dialogStage.close();
    }
	 public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	}
	
	@FXML
	private void concurso() {
		this.mainApp.showConcursos();
		
	}
}
