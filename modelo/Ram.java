import java.awt.Image;

public class Ram extends Componente {

	protected String tipo;
	protected String frecuencia;
	protected String capacidad;
	
	public Ram(String descripcion, String nombre, Image imagen, int stock, String tipo, String frecuencia,
			String capacidad) {
		super(descripcion, nombre, imagen, stock);
		this.tipo = tipo;
		this.frecuencia = frecuencia;
		this.capacidad = capacidad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	
	
}
