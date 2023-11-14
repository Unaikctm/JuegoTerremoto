package objeto;

public class Terremoto {
	private String fecha;
	private String hora;
	private double latitude;
	private double longitud;
	private double profundidad;
	private double magnitud;
	private String tipoMag;
	private String localización;
	private Boolean peligroso;
	
	public Terremoto (String fecha, String hora, double latitude, double longitud, 
			double profundidad, double magnitud, String tipoMag, String localización) {
		this.fecha=fecha;
		this.hora=hora;
		this.latitude=latitude;
		this.longitud=longitud;
		this.profundidad=profundidad;
		this.magnitud=magnitud;
		if (tipoMag.equalsIgnoreCase("mbLg") || tipoMag.equalsIgnoreCase("mb") || tipoMag.equalsIgnoreCase("Mw")) {
			this.tipoMag=tipoMag;
		}
		this.localización=localización;
		if (magnitud>4.2) {
			peligroso=true;
		}
		else {
			peligroso=false;
		}
	}
	public Boolean getPeligroso() {
		if (magnitud>3.7) {
			peligroso=true;
		}
		else {
			peligroso=false;
		}
		return peligroso;
	}
	public void setPeligroso(Boolean peligroso) {
		this.peligroso = peligroso;
	}
	public Terremoto() {}

	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(double profundidad) {
		this.profundidad = profundidad;
	}

	public double getMagnitud() {
		return magnitud;
	}

	public void setMagnitud(double magnitud) {
		this.magnitud = magnitud;
	}

	public String getTipoMag() {
		return tipoMag;
	}

	public void setTipoMag(String tipoMag) {
		this.tipoMag = tipoMag;
	}

	public String getLocalización() {
		return localización;
	}

	public void setLocalización(String localización) {
		this.localización = localización;
	}
	@Override
	public String toString() {
		return "Terremoto [fecha=" + fecha + ", hora=" + hora + ", latitude=" + latitude + ", longitud=" + longitud
				+ ", profundidad=" + profundidad + ", magnitud=" + magnitud + ", tipoMag=" + tipoMag + ", localización="
				+ localización + "]";
	}

}
