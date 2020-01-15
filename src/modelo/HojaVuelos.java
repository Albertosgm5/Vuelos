package modelo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HojaVuelos {
	private final StringProperty nombrePiloto;
	private final StringProperty fecha;
	private final IntegerProperty nManga;
	private final IntegerProperty nGrupo;
	private final DoubleProperty tVuelo;
	private final IntegerProperty dAterrizaje;
	private final IntegerProperty aCorte;
	private final IntegerProperty penalizaciones;

	/**
	 * Default constructor.
	 */
	public HojaVuelos() {
		this(null, null);
	}

	public HojaVuelos(String nombrePiloto, String fecha) {
		this.nombrePiloto = new SimpleStringProperty(nombrePiloto);
		this.fecha = new SimpleStringProperty(fecha);
		this.nManga = new SimpleIntegerProperty(0);
		this.nGrupo = new SimpleIntegerProperty(0);
		this.tVuelo = new SimpleDoubleProperty(0);
		this.dAterrizaje = new SimpleIntegerProperty(0);
		this.aCorte = new SimpleIntegerProperty(0);
		this.penalizaciones = new SimpleIntegerProperty(0);

	}

	public StringProperty getNombrePiloto() {
		return nombrePiloto;
	}

	public StringProperty getFecha() {
		return fecha;
	}

	public IntegerProperty getnManga() {
		return nManga;
	}

	public IntegerProperty getnGrupo() {
		return nGrupo;
	}

	public DoubleProperty gettVuelo() {
		return tVuelo;
	}

	public IntegerProperty getdAterrizaje() {
		return dAterrizaje;
	}

	public IntegerProperty getaCorte() {
		return aCorte;
	}

	public IntegerProperty getPenalizaciones() {
		return penalizaciones;
	}

	public void setNombrePiloto(String nombrePiloto) {
		this.nombrePiloto.set(nombrePiloto);
	}

	public void setFecha(String fecha) {
		this.fecha.set(fecha);
	}

	public void setnManga(int nManga) {
		this.nManga.set(nManga);;
	}

	public void setnGrupo(int nGrupo) {
		this.nGrupo.set(nGrupo);;
	}

	public void settVuelo(double tVuelo) {
		this.tVuelo.set(tVuelo);
	}

	public void setdAterrizaje(int dAterrizaje) {
		this.dAterrizaje.set(dAterrizaje);
	}

	public void setaCorte(int aCorte) {
		this.aCorte.set(aCorte);;
	}

	public void setPenalizaciones(int penalizaciones) {
		this.penalizaciones.set(penalizaciones);
	}

	
	
}
