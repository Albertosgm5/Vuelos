package modelo;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.AdaptadorFechaLocal;
public class Concurso {
    private final StringProperty nombre;
    private final StringProperty tipo;
    private final ObjectProperty<LocalDate> fecha;
    private final StringProperty lugar;
    private final ObjectProperty<LocalDate> finRe;
    
   
    
	public Concurso(StringProperty nombre, StringProperty tipo, ObjectProperty<LocalDate> fecha, StringProperty lugar,
			ObjectProperty<LocalDate> finRe) {
		super();
		this.nombre = nombre;
		this.tipo = tipo;
		this.fecha = fecha;
		this.lugar = lugar;
		this.finRe = finRe;
	}
	
	public String getNombre() {
	        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    
    public StringProperty NombreProperty() {
        return nombre;
    }
    public String getTipo() {
        return tipo.get();
	}
	
	public void setTipo(String tipo) {
	    this.tipo.set(tipo);
	}
	
	public StringProperty TipoProperty() {
	    return tipo;  
	}
	public LocalDate getFecha() {
	    return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }
    
    public ObjectProperty<LocalDate> FechaProperty() {
        return fecha;
    }
	public String getLugar() {
        return lugar.get();
	}
	
	public void setLugar(String lugar) {
	    this.lugar.set(lugar);
	}
	
	public StringProperty LugarProperty() {
	    return lugar;
	}
	public LocalDate getFinRe() {
	    return finRe.get();
    }

    public void setFinRe(LocalDate finRe) {
        this.finRe.set(finRe);
    }
    
    public ObjectProperty<LocalDate> FinReProperty() {
        return finRe;
    }
	
    
    
	    
}
