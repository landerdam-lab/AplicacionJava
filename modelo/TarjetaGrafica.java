import java.awt.Image;

public class TarjetaGrafica extends Componente{

	protected String vram;

	public TarjetaGrafica(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta, String vram) {
		super(idComponente, descripcion, nombre, imagen, stock, idMarca, precioVenta);
		this.vram = vram;
	}

	public String getVram() {
		return vram;
	}

	public void setVram(String vram) {
		this.vram = vram;
	}

	
	
	
}
