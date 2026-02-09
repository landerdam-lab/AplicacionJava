import java.awt.Image;

public class DiscoDuro extends Componente{

	protected String tipoAlmacenamiento;
	protected String capacidad;
	
	
	public DiscoDuro(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String tipoAlmacenamiento, String capacidad) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
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
