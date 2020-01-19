package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Piloto;
import modelo.Resultados;
import util.Conexion;

public class ControladorResultados {
    @FXML
    private TableView<Piloto> pilotoTabla;
    @FXML
    private TableColumn<Piloto, String> nombreColumna;
    @FXML
    private TableColumn<Piloto, String> apellidosColumna;
    
    @FXML
    private Label manga1Etiqueta;
    @FXML
    private Label manga2Etiqueta;
    @FXML
    private Label manga3Etiqueta;
    @FXML
    private Label manga4Etiqueta;
    @FXML
    private Label manga5Etiqueta;
    @FXML
    private Label manga6Etiqueta;
   
	@FXML
    private TextField manga1Campo;
    @FXML
    private TextField manga2Campo;
    @FXML
    private TextField manga3Campo;
    @FXML
    private TextField manga4Campo;
    @FXML
    private TextField manga5Campo;
    @FXML
    private TextField manga6Campo;
    
   
    
   

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ControladorResultados() {
    }

    private int resultado(double tiempo, double metros, double altura) {
    	double resul = 0;
    	double pMetros=0;
    	if(metros==0) {
    		pMetros=50;
    	}else if(metros<4){
    		double pNega=5*metros;
    		pMetros=pMetros-pNega;
    	}else if(metros==4) {
    		pMetros=35;
    	}else if(metros<=10){
    		double pNega=5*metros;
    		pMetros=pMetros+5-pNega;
    	}else {
    		pMetros=0;
    	}
    	
    	double pAltura;
    	if(altura>200) {
    		pAltura=3;
    	}else {
    		pAltura=0.5*altura;
    	}
    	if(tiempo<600) {
    		resul=tiempo+pMetros-pAltura;
    	}else {
    		resul=0;
    	}
    	return (int) resul;
    }
    private double resultadoFinal(int manga1, int manga2, int manga3, int manga4, int manga5, int manga6) {
    	double resul = manga1+manga2+manga3+manga4+manga5+manga6;
    	if(manga1<manga2 && manga2<manga3 && manga3<manga4 && manga4<manga5 && manga5<manga6) {
    		
    		resul=resul-manga1;
    		    
    	}else {
    		if(manga2<manga3 && manga3<manga4 && manga4<manga5 && manga5<manga6) {
    			
    		    resul=resul-manga2;
    		    
    		}else {
    			if(manga3<manga4 && manga4<manga5 && manga5<manga6) {
    				
	    			resul=resul-manga3;
    		    	
        		}else {
        			if(manga4<manga5 && manga5<manga6) {
    					
		    			resul=resul-manga4;
    		    		
    	    		}else {
    	    			if(manga5<manga6) {
    		    			resul=resul-manga5;
    		    		}else {
    		    			resul=resul-manga6;
    		    		}
    	    		}
        		}
    		}
    	}
    	return resul;
    }
    
    /**
     * Called when the user clicks on the delete button.
     */
    
    
}
