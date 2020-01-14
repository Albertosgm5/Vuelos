package modelo;

import java.time.LocalDate;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class Piloto {
	
	    private final StringProperty nombre;
	    private final StringProperty apellidos;
	    private final StringProperty contrasenia;
	    private final StringProperty club;
	    private final StringProperty email;
	    private final StringProperty licencia;
	    private final StringProperty pais;
	    private final StringProperty calle;
	    private final StringProperty ciudad;
	    private final StringProperty provincia;
	    private final IntegerProperty telefono;
	    private final IntegerProperty codigoPostal;
	    /**
	     * Default constructor.
	     */
	    public Piloto() {
	        this(null, null, null, null, null, null, null, null, null, null, 0, 0);
	    }
	    
	    /**
	     * Constructor with some initial data.
	     * 
	     * @param firstName
	     * @param lastName
	     */
	    public Piloto(String nombre, String apellidos, String contrasenia, String club, String email, String licencia, String pais, String calle, String ciudad, String provincia, int telefono, int codigoPostal) {
	        this.nombre = new SimpleStringProperty(nombre);
	        this.apellidos = new SimpleStringProperty(apellidos);
	        this.contrasenia = new SimpleStringProperty(contrasenia);
	        this.club = new SimpleStringProperty(club);
	        this.email = new SimpleStringProperty(email);
	        this.licencia = new SimpleStringProperty(licencia);
	        this.pais = new SimpleStringProperty(pais);
	        this.calle = new SimpleStringProperty(calle);
	        this.ciudad = new SimpleStringProperty(ciudad);
	        this.provincia = new SimpleStringProperty(provincia);
	        this.telefono = new SimpleIntegerProperty(telefono);
	        this.codigoPostal = new SimpleIntegerProperty(codigoPostal);
	        
	   
	    }
	    
	    public String getNombre() {
	        return nombre.get();
	    }

	    public void setNombre(String nombre) {
	        this.nombre.set(nombre);
	    }
	    
	    public StringProperty nombreProperty() {
	        return nombre;
	    }

	    public String getApellidos() {
	        return apellidos.get();
	    }

	    public void setApellidos(String apellidos) {
	        this.apellidos.set(apellidos);
	    }
	    
	    public StringProperty apellidosProperty() {
	        return apellidos;
	    }
	    
	    public String getContrasenia() {
	        return contrasenia.get();
	    }

	    public void setContrasenia(String contrasenia) {
	        this.contrasenia.set(contrasenia);
	    }
	    
	    public StringProperty contraseniaProperty() {
	        return contrasenia;
	    }

	    public String getClub() {
	        return club.get();
	    }

	    public void setClub(String club) {
	        this.club.set(club);
	    }
	    
	    public StringProperty clubProperty() {
	        return club;
	    }
	    
	    public String getEmail() {
	        return email.get();
	    }

	    public void setEmail(String email) {
	        this.email.set(email);
	    }
	    
	    public StringProperty emailProperty() {
	        return email;
	    }
	    
	    public String getLicencia() {
	        return licencia.get();
	    }

	    public void setLicencia(String licencia) {
	        this.licencia.set(licencia);
	    }
	    
	    public StringProperty licenciaProperty() {
	        return licencia;
	    }

	    public String getPais() {
	        return pais.get();
	    }

	    public void setPais(String pais) {
	        this.pais.set(pais);
	    }
	    
	    public StringProperty paisProperty() {
	        return pais;
	    }

	    public String getCalle() {
	        return calle.get();
	    }

	    public void setCalle(String calle) {
	        this.calle.set(calle);
	    }
	    
	    public StringProperty calleProperty() {
	        return calle;
	    }

	    public String getCiudad() {
	        return ciudad.get();
	    }

	    public void setCiudad(String ciudad) {
	        this.ciudad.set(ciudad);
	    }
	    
	    public StringProperty ciudadProperty() {
	        return ciudad;
	    }

	    public String getProvincia() {
	        return provincia.get();
	    }

	    public void setProvincia(String provincia) {
	        this.provincia.set(provincia);
	    }
	    
	    public StringProperty provinciaProperty() {
	        return provincia;
	    }

	    public int getTelefono() {
	        return telefono.get();
	    }

	    public void setTelefono(int telefono) {
	        this.telefono.set(telefono);
	    }
	    
	    public IntegerProperty telefonoProperty() {
	        return telefono;
	    }

	    public int getCodigoPostal() {
	        return codigoPostal.get();
	    }

	    public void setCodigoPostal(int codigoPostal) {
	        this.codigoPostal.set(codigoPostal);
	    }
	    
	    public IntegerProperty codigoPostalProperty() {
	        return codigoPostal;
	    }

	    
	
}