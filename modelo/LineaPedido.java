
public class LineaPedido {
    private int idComponente;
    private int cantidad;
    private double precioUnitario;

    public LineaPedido(int idComponente, int cantidad, double precioUnitario) {
        this.idComponente = idComponente;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

	public int getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(int idComponente) {
		this.idComponente = idComponente;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

    
}