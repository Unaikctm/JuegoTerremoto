package clases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Jugador {
	private String nombreCompleto;
	private String numero;
	private Date fechaNac;
	private Double sueldo;
	private SimpleDateFormat sdt = new SimpleDateFormat("dd/MM/yyyy");
	
	public Jugador(String nombreCompleto, String numero, Date fechaNac, Double sueldo) {
		this.nombreCompleto = nombreCompleto;
		this.numero = numero;
		this.fechaNac = fechaNac;
		this.sueldo = sueldo;
	}
	public Jugador() {}
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}
	public Double getSueldo() {
		return sueldo;
	}
	public void setSueldo(Double sueldo) {
		this.sueldo = sueldo;
	}
	@Override
	public String toString() {
		return nombreCompleto + ": Numero=" + numero + ", fechaNac=" + sdt.format(fechaNac)
				+ ", sueldo=" + sueldo;
	}
	
	public String formatoFichero() {
		return nombreCompleto+";"+numero+";"+sdt.format(fechaNac)+";"+sueldo;
	}
}
