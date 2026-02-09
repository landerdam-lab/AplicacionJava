import java.awt.Image;

public class Refrigeracion extends Componente{

	protected String tipo;
	protected String tamanio;
	
	
	public Refrigeracion(String descripcion, String nombre, Image imagen, int stock, String tipo, String tamanio) {
		super(descripcion, nombre, imagen, stock);
		this.tipo = tipo;
		this.tamanio = tamanio;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getTamanio() {
		return tamanio;
	}


	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}
	
	
	
}
