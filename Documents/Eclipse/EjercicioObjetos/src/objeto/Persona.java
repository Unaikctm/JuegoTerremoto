package objeto;

public class Persona {
	private String nombre;
	private double altura;
	private double peso;
	private Boolean atletico;
	private int vidas;
	
	public Persona(String nombre, double altura, double peso, Boolean atletico) {
		this.nombre=nombre;
		this.altura=altura;
		this.peso=peso;
		this.atletico=atletico;
		this.vidas=10;
	}
	
	public Persona(String nombre) {
		this.nombre=nombre;
	}
	
	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		if(vidas>10) {
			this.vidas=10;
		}else {
			this.vidas = vidas;
		}
	}

	public Persona() {}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Boolean getAtletico() {
		return atletico;
	}

	public void setAtletico(Boolean atletico) {
		this.atletico = atletico;
	}

	@Override
	public String toString() {
		return nombre +" [altura=" + altura + ", peso=" + peso + ", atletico=" + atletico + "]";
	}
	
}
