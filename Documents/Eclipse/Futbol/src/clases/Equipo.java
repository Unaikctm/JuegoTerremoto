package clases;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Equipo {
	private String nombre;
	private String estadio;
	private ArrayList<Jugador> listaJugadores = new ArrayList<Jugador>();
	
	public Equipo(String nombre, String estadio, ArrayList<Jugador> listaJugadores) {
		this.nombre = nombre;
		this.estadio = estadio;
		this.listaJugadores = listaJugadores;
	}

	public Equipo() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstadio() {
		return estadio;
	}

	public void setEstadio(String estadio) {
		this.estadio = estadio;
	}

	public ArrayList<Jugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(ArrayList<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	@Override
	public String toString() {
		return nombre + ": estadio=" + estadio + ", listaJugadores=" + listaJugadores;
	}
	
	public void addJugador(Jugador jugador) {
		listaJugadores.add(jugador);
	}

	public Double gastoEnSueldos() {
		Double total=0.0;
		for (Jugador j:listaJugadores) {
			total=total+j.getSueldo();
		}
		return total;
	}
	
	public void guardarEnFichero() throws IOException {
		FileWriter fw = new FileWriter(nombreFichero());
		for (Jugador j:listaJugadores) {
			fw.write(j.formatoFichero()+"\n");
		}
		fw.close();
	}
	
	private String nombreFichero() {
		return "datos/"+getNombre().toLowerCase().replace(' ','_') + ".txt";
	}
}
