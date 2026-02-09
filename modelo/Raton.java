import java.awt.Image;

public class Raton extends Componente{

	protected String dpi;
	protected String tipo;
	
	public Raton(String descripcion, String nombre, Image imagen, int stock, String dpi, String tipo) {
		super(descripcion, nombre, imagen, stock);
		this.dpi = dpi;
		this.tipo = tipo;
	}

	public String getDpi() {
		return dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
