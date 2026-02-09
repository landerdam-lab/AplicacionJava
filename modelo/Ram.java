import java.awt.Image;

public class Ram extends Componente {

	protected String tipo;
	protected String frecuencia;
	protected String capacidad;
	
	public Ram(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String tipo, String frecuencia, String capacidad) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
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
