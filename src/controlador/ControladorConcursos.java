package controlador;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.ICriterion;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Concurso;
import modelo.Piloto;
import util.Fecha;

public class ControladorConcursos {
	private MainApp mainApp;
	
    @FXML
    private TableView<Piloto> concursoTabla;
    @FXML
    private TableColumn<Piloto, String> combreColumna;
	
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
    @FXML
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
    private void handleDeleteConcurso() {
    	int selectedIndex = concursoTabla.getSelectionModel().getSelectedIndex();
    	//recogemos los datos necesarios para realizar los criterios de la consulta
    	String nombre =(String) concursoTabla.getSelectionModel().getSelectedItem().getNombre();
    	if (selectedIndex >= 0) {
            concursoTabla.getItems().remove(selectedIndex);
         // conectamos con la base de datos, si no existe se crea
            ODB odb = ODBFactory.open("VUELOS.DB");
         // Cogemos los criterios para la consulta
            ICriterion criterio = new And().add(Where.equal("nombre", nombre));
         // Hacemos la consulta 
            IQuery query = new CriteriaQuery(Concurso.class, criterio);
         // Cargamos los objetos que coincidan con esa consulta
            Objects<Concurso> objects = odb.getObjects(query);
         // Nos posicionamos en el primer resultado
            Concurso con=(Concurso) objects.getFirst();
         // Y lo borramos
            odb.delete(con);
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
    @FXML
    private void handleEdit() {
        //if (isInputValid()) {
             concurso.setNombre(nombre.getText());
             concurso.setTipo(tipo.getText());
             concurso.setFecha(Fecha.parse(fecha.getText()));
             concurso.setLugar(lugar.getText());
             concurso.setFinRe(Fecha.parse(finRe.getText()));
            
             // conectamos con la base de datos, si no existe se crea
             ODB odb = ODBFactory.open("VUELOS.DB");
             // Cogemos los criterios para la consulta
             ICriterion criterio = new And().add(Where.equal("nombre", nombre.getText()));
          // Hacemos la consulta 
             IQuery query = new CriteriaQuery(Concurso.class, criterio);
          // Cargamos los objetos que coincidan con esa consulta
             Objects<Concurso> objects = odb.getObjects(query);
          // Nos posicionamos en el primer resultado
             Concurso concurso=(Concurso) objects.getFirst();
          // A la persona sobre la que hacemos la consulta le pasamos sus nuevos datos
          // Con esto conseguimos modificar sus datos en la base de datos
     		concurso.setNombre(nombre.getText());
     		concurso.setTipo(tipo.getText());
     		concurso.setFecha(Fecha.parse(fecha.getText()));
     		concurso.setLugar(lugar.getText());
     		concurso.setFinRe(Fecha.parse(finRe.getText()));
     		odb.store(concurso);
     		
             okClicked = true;
             dialogStage.close();
             odb.close();
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
