
public class LineaPedido {
    private int idComponente;
    private int cantidad;
    private double precioUnitario;

    public LineaPedido(int idComponente, int cantidad, double precioUnitario) {
        this.idComponente = idComponente;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getIdComponente() { return idComponente; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
}