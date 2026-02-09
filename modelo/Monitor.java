import java.awt.Image;

public class Monitor extends Componente {

	protected String hz;
	protected String medidas;
	
	
	public Monitor(String descripcion, String nombre, Image imagen, int stock, String hz, String medidas) {
		super(descripcion, nombre, imagen, stock);
		this.hz = hz;
		this.medidas = medidas;
	}


	public String getHz() {
		return hz;
	}


	public void setHz(String hz) {
		this.hz = hz;
	}


	public String getMedidas() {
		return medidas;
	}


	public void setMedidas(String medidas) {
		this.medidas = medidas;
	}
	
	
}
