import java.awt.Image;

public class Teclado extends Componente{

	protected String tipo;
	protected String tipoCable;
	
	
	public Teclado(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String tipo, String tipoCable) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.tipo = tipo;
		this.tipoCable = tipoCable;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getTipoCable() {
		return tipoCable;
	}


	public void setTipoCable(String tipoCable) {
		this.tipoCable = tipoCable;
	}
	
	
	
	
	
}
