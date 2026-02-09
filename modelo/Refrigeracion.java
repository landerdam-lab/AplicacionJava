import java.awt.Image;

public class Refrigeracion extends Componente{

	protected String tipo;
	protected String tamanio;
	
	
	public Refrigeracion(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String tipo, String tamanio) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
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
