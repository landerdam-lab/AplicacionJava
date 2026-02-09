import java.awt.Image;

public class Caja extends Componente {

	protected String dimensiones;
	protected String puertosFrontales;
	
	public Caja(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String dimensiones, String puertosFrontales) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.dimensiones = dimensiones;
		this.puertosFrontales = puertosFrontales;
	}

	public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public String getPuertosFrontales() {
		return puertosFrontales;
	}

	public void setPuertosFrontales(String puertosFrontales) {
		this.puertosFrontales = puertosFrontales;
	}
	
	
	
	
}
