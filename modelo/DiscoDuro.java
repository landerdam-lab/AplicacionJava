import java.awt.Image;

public class DiscoDuro extends Componente{

	protected String tipoAlmacenamiento;
	protected String capacidad;
	
	
	public DiscoDuro(String descripcion, String nombre, Image imagen, int stock, String tipoAlmacenamiento,
			String capacidad) {
		super(descripcion, nombre, imagen, stock);
		this.tipoAlmacenamiento = tipoAlmacenamiento;
		this.capacidad = capacidad;
	}


	public String getTipoAlmacenamiento() {
		return tipoAlmacenamiento;
	}


	public void setTipoAlmacenamiento(String tipoAlmacenamiento) {
		this.tipoAlmacenamiento = tipoAlmacenamiento;
	}


	public String getCapacidad() {
		return capacidad;
	}


	public void setCapacidad(String capacidad) {
		this.capacidad = capacidad;
	}
	
	
}
