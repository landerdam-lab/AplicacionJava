import java.awt.Image;

public class PlacaBase extends Componente {

	protected String socket;
	protected String factorForma;
	
	
	public PlacaBase(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String socket, String factorForma) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.socket = socket;
		this.factorForma = factorForma;
	}


	public String getSocket() {
		return socket;
	}


	public void setSocket(String socket) {
		this.socket = socket;
	}


	public String getFactorForma() {
		return factorForma;
	}


	public void setFactorForma(String factorForma) {
		this.factorForma = factorForma;
	}
	
	
	
	
}
