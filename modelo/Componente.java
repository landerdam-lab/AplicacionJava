import java.awt.Image;

public class Componente {

	private String descripcion;
	private String nombre;
	private Image imagen;
	private int stock;
	
	public Componente(String descripcion, String nombre, Image imagen, int stock) {
		super();
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.imagen = imagen;
		this.stock = stock;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
