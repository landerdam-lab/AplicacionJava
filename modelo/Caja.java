import java.awt.Image;

public class Caja extends Componente {

	protected String dimensiones;
	protected String puertosFrontales;
	
	public Caja(String descripcion, String nombre, Image imagen, int stock, String dimensiones,
			String puertosFrontales) {
		super(descripcion, nombre, imagen, stock);
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
