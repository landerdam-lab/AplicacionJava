import java.awt.Image;

public class PlacaBase extends Componente {

	protected String socket;
	protected String factorForma;
	
	
	public PlacaBase(String descripcion, String nombre, Image imagen, int stock, String socket, String factorForma) {
		super(descripcion, nombre, imagen, stock);
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
