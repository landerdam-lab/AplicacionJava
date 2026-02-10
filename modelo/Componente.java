import java.awt.Image;

public class Componente {

	private int idComponente;
	private String descripcion;
	private String nombre;
	private Image imagen;
	private int stock;
	private int idMarca;
	protected double precioVenta;
	
	public Componente(int idComponente, String descripcion, String nombre, Image imagen, int stock, int idMarca,
			double precioVenta) {
		super();
		this.idComponente = idComponente;
		this.descripcion = descripcion;
		this.nombre = nombre;
		this.imagen = imagen;
		this.stock = stock;
		this.idMarca = idMarca;
		this.precioVenta = precioVenta;
	}

	public int getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(int idComponente) {
		this.idComponente = idComponente;
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

	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}
	

    @Override
    public String toString() {
        return nombre + " - " + precioVenta + "€ (Stock: " + stock + ")";
    }
}
	

