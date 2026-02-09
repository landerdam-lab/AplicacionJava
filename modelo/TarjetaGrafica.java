import java.awt.Image;

public class TarjetaGrafica extends Componente{

	protected String vram;

	public TarjetaGrafica(String descripcion, String nombre, Image imagen, int stock, String vram) {
		super(descripcion, nombre, imagen, stock);
		this.vram = vram;
	}

	public String getVram() {
		return vram;
	}

	public void setVram(String vram) {
		this.vram = vram;
	}
	
	
	
	
}
